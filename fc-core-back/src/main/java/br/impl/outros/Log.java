package br.impl.outros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	private static final Logger logger = LoggerFactory.getLogger("");
	
	public static void info(String s) {
		logger.info(s);
	}
	public static void debug(String s) {
		logger.debug(s);
	}
	public static void error(String s) {
		logger.error(s);
	}
	public static void warn(String s) {
		logger.warn(s);
	}
}
