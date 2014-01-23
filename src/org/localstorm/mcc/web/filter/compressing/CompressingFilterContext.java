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

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Encapsulates the {@link CompressingFilter} environment, including configuration and runtime statistics. This object
 * may be conveniently passed around in the code to make this information available.
 *
 * @author Sean Owen
 * @author Peter Bryant
 */
final class CompressingFilterContext {

	private static final int DEFAULT_COMPRESSION_THRESHOLD = 1024;

	private final boolean debug;
	private final CompressingFilterLoggerImpl logger;
	private final int compressionThreshold;
	private final ServletContext servletContext;
	private final CompressingFilterStats stats;
	private final boolean includeContentTypes;
	private final Collection<String> contentTypes;
	// Thanks to Peter Bryant for suggesting this functionality:
	private final boolean includePathPatterns;
	private final Collection<Pattern> pathPatterns;
	// Thanks to reimerl for proposing this + sample code
	private final boolean includeUserAgentPatterns;
	private final Collection<Pattern> userAgentPatterns;

	CompressingFilterContext(final FilterConfig filterConfig) throws ServletException {

		assert filterConfig != null;

		debug = readBooleanValue(filterConfig, "debug");

		final String javaUtilDelegateName = filterConfig.getInitParameter("javaUtilLogger");
		if (javaUtilDelegateName != null) {
			logger = new CompressingFilterLoggerImpl(filterConfig.getServletContext(),
			                                         debug,
			                                         javaUtilDelegateName,
			                                         true);
		} else {
			final String jakartaCommonsDelegateName =
				filterConfig.getInitParameter("jakartaCommonsLogger");
			if (jakartaCommonsDelegateName != null) {
				logger = new CompressingFilterLoggerImpl(filterConfig.getServletContext(),
				                                         debug,
				                                         jakartaCommonsDelegateName,
				                                         false);
			} else {
				logger = new CompressingFilterLoggerImpl(filterConfig.getServletContext(),
				                                         debug,
				                                         null,
				                                         false);
			}
		}

		logger.logDebug("Debug logging statements are enabled");

		compressionThreshold = readCompressionThresholdValue(filterConfig);
		if (logger.isDebug()) {
			logger.logDebug("Using compressing threshold: " + compressionThreshold);
		}

		servletContext = filterConfig.getServletContext();
		assert this.servletContext != null;

		if (readBooleanValue(filterConfig, "statsEnabled")) {
			stats = new CompressingFilterStats();
			ensureStatsInContext();
			logger.logDebug("Stats are enabled");
		} else {
			stats = null;
			logger.logDebug("Stats are disabled");
		}

		final String includeContentTypesString = filterConfig.getInitParameter("includeContentTypes");
		final String excludeContentTypesString = filterConfig.getInitParameter("excludeContentTypes");
		if (includeContentTypesString != null && excludeContentTypesString != null) {
			throw new IllegalArgumentException("Can't specify both includeContentTypes and excludeContentTypes");
		}

		if (includeContentTypesString == null) {
			includeContentTypes = false;
			contentTypes = parseContentTypes(excludeContentTypesString);
		} else {
			includeContentTypes = true;
			contentTypes = parseContentTypes(includeContentTypesString);
		}

		if (!contentTypes.isEmpty()) {
			logger.logDebug("Filter will " + (includeContentTypes ? "include" : "exclude") +
							" only these content types: " + contentTypes);
		}

		final String includePathPatternsString = filterConfig.getInitParameter("includePathPatterns");
		final String excludePathPatternsString = filterConfig.getInitParameter("excludePathPatterns");
		if (includePathPatternsString != null && excludePathPatternsString != null) {
			throw new IllegalArgumentException("Can't specify both includePathPatterns and excludePathPatterns");
		}

		if (includePathPatternsString == null) {
			includePathPatterns = false;
			pathPatterns = parsePatterns(excludePathPatternsString);
		} else {
			includePathPatterns = true;
			pathPatterns = parsePatterns(includePathPatternsString);
		}

		if (!pathPatterns.isEmpty() && logger.isDebug()) {
			logger.logDebug("Filter will " + (includePathPatterns ? "include" : "exclude") +
			                " only these file patterns: " + pathPatterns);
		}

		final String includeUserAgentPatternsString = filterConfig.getInitParameter("includeUserAgentPatterns");
		final String excludeUserAgentPatternsString = filterConfig.getInitParameter("excludeUserAgentPatterns");
		if (includeUserAgentPatternsString != null && excludeUserAgentPatternsString != null) {
			throw new IllegalArgumentException(
				"Can't specify both includeUserAgentPatterns and excludeUserAgentPatterns");
		}

		if (includeUserAgentPatternsString == null) {
			includeUserAgentPatterns = false;
			userAgentPatterns = parsePatterns(excludeUserAgentPatternsString);
		} else {
			includeUserAgentPatterns = true;
			userAgentPatterns = parsePatterns(includeUserAgentPatternsString);
		}

		if (!userAgentPatterns.isEmpty() && logger.isDebug()) {
			logger.logDebug("Filter will " + (includeUserAgentPatterns ? "include" : "exclude") +
			                " only these User-Agent patterns: " + userAgentPatterns);
		}

	}

	boolean isDebug() {
		return debug;
	}

	CompressingFilterLoggerImpl getLogger() {
		assert logger != null;
		return logger;
	}

	int getCompressionThreshold() {
		return compressionThreshold;
	}

	CompressingFilterStats getStats() {
		if (stats == null) {
			throw new IllegalStateException("Stats are not enabled");
		}
		ensureStatsInContext();
		return stats;
	}

	boolean isStatsEnabled() {
		return stats != null;
	}

	boolean isIncludeContentTypes() {
		return includeContentTypes;
	}

	Collection<String> getContentTypes() {
		return contentTypes;
	}

	boolean isIncludePathPatterns() {
		return includePathPatterns;
	}

	Iterable<Pattern> getPathPatterns() {
		return pathPatterns;
	}

	boolean isIncludeUserAgentPatterns() {
		return includeUserAgentPatterns;
	}

	Iterable<Pattern> getUserAgentPatterns() {
		return userAgentPatterns;
	}

	@Override
	public String toString() {
		return "CompressingFilterContext";
	}

	private void ensureStatsInContext() {
		assert servletContext != null;
		if (servletContext.getAttribute(CompressingFilterStats.STATS_KEY) == null) {
			servletContext.setAttribute(CompressingFilterStats.STATS_KEY, stats);
		}
	}

	private static boolean readBooleanValue(final FilterConfig filterConfig, final String parameter) {
		return Boolean.valueOf(filterConfig.getInitParameter(parameter));
	}

	private static int readCompressionThresholdValue(final FilterConfig filterConfig) throws ServletException {
		final String compressionThresholdString = filterConfig.getInitParameter("compressionThreshold");
		final int value;
		if (compressionThresholdString != null) {
			try {
				value = Integer.parseInt(compressionThresholdString);
			} catch (NumberFormatException nfe) {
				throw new ServletException("Invalid compression threshold: " + compressionThresholdString, nfe);
			}
			if (value < 0) {
				throw new ServletException("Compression threshold cannot be negative");
			}
		} else {
			value = DEFAULT_COMPRESSION_THRESHOLD;
		}
		return value;
	}

	private static Collection<String> parseContentTypes(final String contentTypesString) {
		if (contentTypesString == null) {
			return Collections.emptyList();
		}
		final List<String> contentTypes = new ArrayList<String>(5);
		for (final String contentType : contentTypesString.split(",")) {
			if (contentType.length() > 0) {
				contentTypes.add(contentType);
			}
		}
		return Collections.unmodifiableList(contentTypes);
	}

	private static Collection<Pattern> parsePatterns(final String patternsString) {
		if (patternsString == null) {
			return Collections.emptyList();
		}
		final List<Pattern> patterns = new ArrayList<Pattern>(5);
		for (final String pattern : patternsString.split(",")) {
			if (pattern.length() > 0) {
				patterns.add(Pattern.compile(pattern));
			}
		}
		return Collections.unmodifiableList(patterns);
	}

}
