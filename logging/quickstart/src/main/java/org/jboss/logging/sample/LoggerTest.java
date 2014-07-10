package org.jboss.logging.sample;

import org.jboss.logging.Logger;

public class LoggerTest {

	public static void main(String[] args) {
		
		System.setProperty("org.jboss.logging.provider", "jboss");

		Logger logger = Logger.getLogger(LoggerTest.class, "com.kylin.test");
		
		logger.info("logger test");
	}

}
