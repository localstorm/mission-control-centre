/*
 * Copyright 2004 and onwards Sean Owen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.localstorm.mcc.web.filter.compressing;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <p>Implementations of this abstract class can add compression of a particular type to a given {@link OutputStream}.
 * They each return a {@link CompressingOutputStream}, which is just a thin wrapper on top of an {@link OutputStream} that
 * adds the ability to "finish" a stream (see {@link CompressingOutputStream}).</p>
 * <p/>
 * <p>This class contains implementations based on several popular compression algorithms, such as gzip. For example,
 * the gzip implementation can decorate an {@link OutputStream} using an instance of {@link GZIPOutputStream} and in
 * that way add gzip compression to the stream.</p>
 *
 * @author Sean Owen
 */
abstract class CompressingStreamFactory {

	/**
	 * Implementation based on {@link GZIPOutputStream} and {@link GZIPInputStream}.
	 */
	private static final CompressingStreamFactory GZIP_CSF = new GZIPCompressingStreamFactory();

	/**
	 * Implementation based on {@link ZipOutputStream} and {@link ZipInputStream}.
	 */
	private static final CompressingStreamFactory ZIP_CSF = new ZipCompressingStreamFactory();

	/**
	 * Implementation based on {@link DeflaterOutputStream}.
	 */
	private static final CompressingStreamFactory DEFLATE_CSF = new DeflateCompressingStreamFactory();

	/**
	 * "No encoding" content type: "identity".
	 */
	static final String NO_ENCODING = "identity";

	private static final String GZIP_ENCODING = "gzip";
	private static final String X_GZIP_ENCODING = "x-gzip";
	private static final String DEFLATE_ENCODING = "deflate";
	private static final String COMPRESS_ENCODING = "compress";
	private static final String X_COMPRESS_ENCODING = "x-compress";

	/** "Any encoding" content type: the "*" wildcard. */
	private static final String ANY_ENCODING = "*";

	/** Ordered list of preferred encodings, from most to least preferred */
	private static final List<String> supportedEncodings;
	static {
		final List<String> temp = new ArrayList<String>(6);
		temp.add(GZIP_ENCODING);
		temp.add(DEFLATE_ENCODING);
		temp.add(COMPRESS_ENCODING);
		temp.add(X_GZIP_ENCODING);
		temp.add(X_COMPRESS_ENCODING);
		temp.add(NO_ENCODING);
		supportedEncodings = Collections.unmodifiableList(temp);
	}

	/**
	 * Cache mapping previously seen "Accept-Encoding" header Strings to an appropriate instance of {@link
	 * CompressingStreamFactory}.
	 */
	private static final Map<String, String> bestEncodingCache =
	    Collections.synchronizedMap(new HashMap<String, String>(101));
	/**
	 * Maps content type String to appropriate implementation of {@link CompressingStreamFactory}.
	 */
	private static final Map<String, CompressingStreamFactory> factoryMap;

	static {
		final Map<String, CompressingStreamFactory> temp = new HashMap<String, CompressingStreamFactory>(11);
		temp.put(GZIP_ENCODING, GZIP_CSF);
		temp.put(X_GZIP_ENCODING, GZIP_CSF);
		temp.put(COMPRESS_ENCODING, ZIP_CSF);
		temp.put(X_COMPRESS_ENCODING, ZIP_CSF);
		temp.put(DEFLATE_ENCODING, DEFLATE_CSF);
		factoryMap = Collections.unmodifiableMap(temp);
	}


	abstract CompressingOutputStream getCompressingStream(OutputStream servletOutputStream,
	                                                      CompressingFilterContext context) throws IOException;

	abstract CompressingInputStream getCompressingStream(InputStream servletInputStream,
	                                                     CompressingFilterContext context) throws IOException;

	
	private static OutputStream getStatsOutputStream(final OutputStream outputStream,
	                                                 final CompressingFilterContext context,
	                                                 final CompressingFilterStats.StatsField field) {
		assert outputStream != null;
		final OutputStream result;
		if (context.isStatsEnabled()) {
			final CompressingFilterStats stats = context.getStats();
			final CompressingFilterStats.OutputStatsCallback callbackOutput = stats.getOutputStatsCallback(field);
			result = new StatsOutputStream(outputStream, callbackOutput);
		} else {
			result = outputStream;
		}
		return result;
	}

	
	private static InputStream getStatsInputStream(final InputStream inputStream,
	                                               final CompressingFilterContext context,
	                                               final CompressingFilterStats.StatsField field) {
		assert inputStream != null;
		final InputStream result;
		if (context.isStatsEnabled()) {
			final CompressingFilterStats stats = context.getStats();
			final CompressingFilterStats.InputStatsCallback callbackInput = stats.getInputStatsCallback(field);
			result = new StatsInputStream(inputStream, callbackInput);
		} else {
			result = inputStream;
		}
		return result;
	}

	private static boolean isSupportedResponseContentEncoding(final String contentEncoding) {
		return NO_ENCODING.equals(contentEncoding) || factoryMap.containsKey(contentEncoding);
	}

	static boolean isSupportedRequestContentEncoding(final String contentEncoding) {
		// A little kludgy -- deflate isn't supported in requests
		return
			NO_ENCODING.equals(contentEncoding) ||
			(factoryMap.containsKey(contentEncoding) && !DEFLATE_ENCODING.equals(contentEncoding));
	}

	/**
	 * Returns the {@link CompressingStreamFactory} instance associated to the given content encoding.
	 *
	 * @param contentEncoding content encoding (e.g. "gzip")
	 * @return {@link CompressingStreamFactory} for content encoding
	 */
	
	static CompressingStreamFactory getFactoryForContentEncoding(final String contentEncoding) {
		assert factoryMap.containsKey(contentEncoding);
		return factoryMap.get(contentEncoding);
	}

	/**
	 * Determines best content encoding for the response, based on the request -- in particular, based on its
	 * "Accept-Encoding" header.
	 *
	 * @param httpRequest request
	 * @return best content encoding
	 */
	static String getBestContentEncoding(final HttpServletRequest httpRequest) {

		final String forcedEncoding = (String) httpRequest.getAttribute(CompressingFilter.FORCE_ENCODING_KEY);
		String bestEncoding;
		if (forcedEncoding != null) {

			bestEncoding = forcedEncoding;

		} else {

			final String acceptEncodingHeader = httpRequest.getHeader(
					CompressingHttpServletResponse.ACCEPT_ENCODING_HEADER);
			if (acceptEncodingHeader == null) {

				bestEncoding = NO_ENCODING;

			} else {

				bestEncoding = bestEncodingCache.get(acceptEncodingHeader);

				if (bestEncoding == null) {

					// No cached value; must parse header to determine best encoding
					// I don't synchronize on bestEncodingCache; it's not worth it to avoid the rare case where
					// two thread get in here and both parse the header. It's only a tiny bit of extra work, and
					// avoids the synchronization overhead.

					if (acceptEncodingHeader.indexOf((int) ',') >= 0) {
						// multiple encodings are accepted
						bestEncoding = selectBestEncoding(acceptEncodingHeader);
					} else {
						// one encoding is accepted
						bestEncoding = parseBestEncoding(acceptEncodingHeader);
					}

					bestEncodingCache.put(acceptEncodingHeader, bestEncoding);
				}
			}
		}

		// User-specified encoding might not be supported
		if (!isSupportedResponseContentEncoding(bestEncoding)) {
			bestEncoding = NO_ENCODING;
		}

		return bestEncoding;
	}

	
	private static String parseBestEncoding(final String acceptEncodingHeader) {
		final ContentEncodingQ contentEncodingQ = parseContentEncodingQ(acceptEncodingHeader);
		final String contentEncoding = contentEncodingQ.getContentEncoding();
		if (contentEncodingQ.getQ() > 0.0) {
			if (ANY_ENCODING.equals(contentEncoding)) {
				return supportedEncodings.get(0);
			} else if (supportedEncodings.contains(contentEncoding)) {
				return contentEncoding;
			}
		}
		return NO_ENCODING;
	}

	
	private static String selectBestEncoding(final String acceptEncodingHeader) {
		// multiple encodings are accepted; determine best one

		final Collection<String> bestEncodings = new HashSet<String>(3);
		double bestQ = 0.0;
		final Collection<String> unacceptableEncodings = new HashSet<String>(3);
		boolean willAcceptAnything = false;

		for (final String token : acceptEncodingHeader.split(",")) {
			final ContentEncodingQ contentEncodingQ = parseContentEncodingQ(token);
			final String contentEncoding = contentEncodingQ.getContentEncoding();
			final double q = contentEncodingQ.getQ();
			if (ANY_ENCODING.equals(contentEncoding)) {
				willAcceptAnything = q > 0.0;
			} else if (supportedEncodings.contains(contentEncoding)) {
				if (q > 0.0) {
					if (q == bestQ) {
						bestEncodings.add(contentEncoding);
					} else if (q > bestQ) {
						bestQ = q;
						bestEncodings.clear();
						bestEncodings.add(contentEncoding);
					}
				} else {
					unacceptableEncodings.add(contentEncoding);
				}
			}
		}

		if (bestEncodings.isEmpty()) {
			// nothing was acceptable to us
			if (willAcceptAnything) {
				if (unacceptableEncodings.isEmpty()) {
					return supportedEncodings.get(0);
				} else {
					for (final String encoding : supportedEncodings) {
						if (!unacceptableEncodings.contains(encoding)) {
							return encoding;
						}
					}
				}
			}
		} else {
			for (final String encoding : supportedEncodings) {
				if (bestEncodings.contains(encoding)) {
					return encoding;
				}
			}
		}

		return NO_ENCODING;
	}

	
	private static ContentEncodingQ parseContentEncodingQ(final String contentEncodingString) {

		double q = 1.0;

		final int qvalueStartIndex = contentEncodingString.indexOf((int) ';');
		final String contentEncoding;
		if (qvalueStartIndex >= 0) {
			contentEncoding = contentEncodingString.substring(0, qvalueStartIndex).trim();
			final String qvalueString = contentEncodingString.substring(qvalueStartIndex + 1).trim();
			if (qvalueString.startsWith("q=")) {
				try {
					q = Double.parseDouble(qvalueString.substring(2));
				} catch (NumberFormatException nfe) {
					// That's bad -- browser sent an invalid number. All we can do is ignore it, and
					// pretend that no q value was specified, so that it effectively defaults to 1.0
				}
			}
		} else {
			contentEncoding = contentEncodingString.trim();
		}

		return new ContentEncodingQ(contentEncoding, q);
	}


	private static final class ContentEncodingQ {

		private final String contentEncoding;
		private final double q;

		private ContentEncodingQ(final String contentEncoding, final double q) {
			assert contentEncoding != null && contentEncoding.length() > 0;
			this.contentEncoding = contentEncoding;
			this.q = q;
		}

		
		private String getContentEncoding() {
			return contentEncoding;
		}

		private double getQ() {
			return q;
		}

		@Override 
		public String toString() {
			return contentEncoding + ";q=" + q;
		}
	}

	private static class GZIPCompressingStreamFactory extends CompressingStreamFactory {
		@Override 
		CompressingOutputStream getCompressingStream(final OutputStream outputStream,
		                                             final CompressingFilterContext context) throws IOException {
			return new CompressingOutputStream() {
				private final DeflaterOutputStream gzipOutputStream =
				    new GZIPOutputStream(
					    CompressingStreamFactory.getStatsOutputStream(
						    outputStream, context, CompressingFilterStats.StatsField.RESPONSE_COMPRESSED_BYTES));
				private final OutputStream statsOutputStream =
				    CompressingStreamFactory.getStatsOutputStream(
					    gzipOutputStream, context, CompressingFilterStats.StatsField.RESPONSE_INPUT_BYTES);

                @Override
				public OutputStream getCompressingOutputStream() {
					return statsOutputStream;
				}

                @Override
				public void finish() throws IOException {
					gzipOutputStream.finish();
				}
			};
		}

		@Override 
		CompressingInputStream getCompressingStream(final InputStream inputStream,
		                                            final CompressingFilterContext context) {
		    return new CompressingInputStream() {
                @Override
			    public InputStream getCompressingInputStream() throws IOException {
				    return CompressingStreamFactory.getStatsInputStream(
					    new GZIPInputStream(
						    CompressingStreamFactory.getStatsInputStream(
							    inputStream, context, CompressingFilterStats.StatsField.REQUEST_COMPRESSED_BYTES)
					    ),
					    context,
					    CompressingFilterStats.StatsField.REQUEST_INPUT_BYTES);
			    }
		    };
		}
	}

	private static class ZipCompressingStreamFactory extends CompressingStreamFactory {
		@Override 
		CompressingOutputStream getCompressingStream(final OutputStream outputStream,
		                                             final CompressingFilterContext context) {
			return new CompressingOutputStream() {
				private final DeflaterOutputStream zipOutputStream =
				    new ZipOutputStream(
					    CompressingStreamFactory.getStatsOutputStream(
						    outputStream, context, CompressingFilterStats.StatsField.RESPONSE_COMPRESSED_BYTES));
				private final OutputStream statsOutputStream =
				    CompressingStreamFactory.getStatsOutputStream(
					    zipOutputStream, context, CompressingFilterStats.StatsField.RESPONSE_INPUT_BYTES);

                @Override
				public OutputStream getCompressingOutputStream() {
					return statsOutputStream;
				}

                @Override
				public void finish() throws IOException {
					zipOutputStream.finish();
				}
			};
		}

		@Override 
		CompressingInputStream getCompressingStream(final InputStream inputStream,
		                                            final CompressingFilterContext context) {
		    return new CompressingInputStream() {
                @Override
			    public InputStream getCompressingInputStream() {
				    return CompressingStreamFactory.getStatsInputStream(
					    new ZipInputStream(
						    CompressingStreamFactory.getStatsInputStream(
							    inputStream, context, CompressingFilterStats.StatsField.REQUEST_COMPRESSED_BYTES)
					    ),
					    context,
					    CompressingFilterStats.StatsField.REQUEST_INPUT_BYTES);
			    }
		    };
		}
	}

	private static class DeflateCompressingStreamFactory extends CompressingStreamFactory {
		@Override 
		CompressingOutputStream getCompressingStream(final OutputStream outputStream,
		                                             final CompressingFilterContext context) {
			return new CompressingOutputStream() {
				private final DeflaterOutputStream deflaterOutputStream =
				    new DeflaterOutputStream(
					    CompressingStreamFactory.getStatsOutputStream(
						    outputStream, context, CompressingFilterStats.StatsField.RESPONSE_COMPRESSED_BYTES));
				private final OutputStream statsOutputStream =
				    CompressingStreamFactory.getStatsOutputStream(
					    deflaterOutputStream, context, CompressingFilterStats.StatsField.RESPONSE_INPUT_BYTES);

                @Override
				public OutputStream getCompressingOutputStream() {
					return statsOutputStream;
				}

                @Override
				public void finish() throws IOException {
					deflaterOutputStream.finish();
				}
			};
		}

		@Override 
		CompressingInputStream getCompressingStream(final InputStream inputStream,
		                                            final CompressingFilterContext context) {
			throw new UnsupportedOperationException();
		}
	}
}