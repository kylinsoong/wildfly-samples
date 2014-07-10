package org.jboss.logging.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.jboss.logging.Logger;

public class LocalSystemConfig {

	private static final Logger LOGGER = Logger.getLogger(LocalSystemConfig.class);

	public Properties openCustomProperties(String configname)throws FileNotFoundException {
		Properties props = new Properties();
		try {
			LOGGER.info("Loading custom configuration from " + configname);
			props.load(new FileInputStream(configname));
		} catch (IOException e) {
			LOGGER.error("Custom configuration file (" + configname + ") not found. Using defaults.");
			throw new FileNotFoundException(configname);
		}

		return props;
	}

	public static void main(String[] args) throws FileNotFoundException {

		new LocalSystemConfig().openCustomProperties("XXOO");
	}

}
