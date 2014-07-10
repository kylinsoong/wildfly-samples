package org.jboss.logging.sample;

import org.jboss.logging.Logger;

public class HelloWorld {
	
	private static final Logger LOGGER = Logger.getLogger(HelloWorld.class);	

	public static void main(String[] args) {

		LOGGER.trace("TRACE Message");
		LOGGER.debug("DEBUG Message");
		LOGGER.info("INFO Message");
		LOGGER.error("Error Message");
		LOGGER.fatal("FATAL Message");
		
		LOGGER.error("Configuration file not found.");
	}

}
