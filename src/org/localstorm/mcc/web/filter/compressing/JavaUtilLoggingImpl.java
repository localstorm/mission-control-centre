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

import org.apache.log4j.Logger;


/**
 * A {@link CompressingFilterLogger} implementation based on java.util.logging.
 *
 * @author Sean Owen
 */
public final class JavaUtilLoggingImpl implements CompressingFilterLogger {

	private final Logger logger;

	/**
	 * This constructor is public so that it may be instantiated by reflection.
	 *
	 * @param loggerName {@link Logger} name
	 */
	public JavaUtilLoggingImpl(final String loggerName) {
		logger = Logger.getLogger(loggerName);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void log(final String message) {
		logger.info(message);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void log(final String message, final Throwable t) {
		logger.info(message, t);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void logDebug(final String message) {
		logger.debug(message);
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
	public void logDebug(final String message, final Throwable t) {
		logger.debug(message, t);
	}

	@Override
	public String toString() {
		return "JavaUtilLoggingImpl[" + String.valueOf(logger) + ']';
	}

}
