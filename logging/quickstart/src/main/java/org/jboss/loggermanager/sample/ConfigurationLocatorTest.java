package org.jboss.loggermanager.sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.logmanager.DefaultConfigurationLocator;

public class ConfigurationLocatorTest {

	public static void main(String[] args) throws IOException {

		DefaultConfigurationLocator locator = new DefaultConfigurationLocator();
		
		setSystemProperty("logging.configuration", new File("logging.properties").toURI().toURL().toString());
		
		InputStream in = locator.findConfiguration();
		
		System.out.println(in);
	}
	
	private static void setSystemProperty(final String key, final String value) {
        if (System.getSecurityManager() == null) {
            System.setProperty(key, value);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    System.setProperty(key, value);
                    return null;
                }
            });
        }
    }

}
