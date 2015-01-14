package com.mobolt.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MyLogger {

	public static final Logger getLogger() {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		String name = stackTraceElements[2].getClassName();
		return Logger.getLogger(name);
	}

	public static final void log(Level level, Object message) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		String name = stackTraceElements[2].getClassName();
		Logger logger = Logger.getLogger(name);
		logger.log(level, message);
	}

	public static final void log(Level level, Object message, Throwable t) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		String name = stackTraceElements[2].getClassName();
		Logger logger = Logger.getLogger(name);
		logger.log(level, message, t);
	}

}
