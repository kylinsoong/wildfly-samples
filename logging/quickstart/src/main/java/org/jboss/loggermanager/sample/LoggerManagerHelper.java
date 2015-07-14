package org.jboss.loggermanager.sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jboss.logging.sample.HelloWorld;
import org.jboss.logmanager.ConfigurationLocator;
import org.jboss.logmanager.Configurator;
import org.jboss.logmanager.LogContext;
import org.jboss.logmanager.PropertyConfigurator;

public class LoggerManagerHelper {
	
	public static void configureLogManager(String config) {
		
		if (getSystemProperty("java.util.logging.manager") == null) {
			try {
				setSystemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
				setSystemProperty("logging.configuration", config);
				setSystemProperty("org.jboss.logmanager.configurationLocator", TeiidLoggerConfigurationLocator.class.getName());
				LogManager.getLogManager();
			} catch (SecurityException e) {
                System.err.println("ERROR: Could not configure LogManager");
            } catch (Throwable ignored) {
            }
		}
	}
	
	public static void configureLogManager(String logLevel, String logFile) {
		if (getSystemProperty("java.util.logging.manager") == null) {
			try {
				setSystemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
				
				final LogManager logManager = LogManager.getLogManager();
                if (logManager instanceof org.jboss.logmanager.LogManager) {
                	if (LogContext.getSystemLogContext().getAttachment("", Configurator.ATTACHMENT_KEY) == null){
                		final PropertyConfigurator configurator = new PropertyConfigurator();
                		final Configurator appearing = LogContext.getSystemLogContext().getLogger("").attachIfAbsent(Configurator.ATTACHMENT_KEY, configurator);
                		if (appearing == null) {
                            configurator.configure(createLogManagerConfig(logLevel, logFile));
                        }
                	}
                }
			} catch (SecurityException e) {
                System.err.println("ERROR: Could not configure LogManager");
            } catch (Throwable ignored) {
            	ignored.printStackTrace();
            }
		}
	}
	
	private static Properties createLogManagerConfig(String logLevel, String logFile) {
		final Properties properties = new Properties();
        // Root log level
        properties.setProperty("logger.level", logLevel.toUpperCase(Locale.ENGLISH));
        properties.setProperty("logger.handlers", "FILE,CONSOLE");
        
        properties.setProperty("handler.CONSOLE", "org.jboss.logmanager.handlers.ConsoleHandler");
        properties.setProperty("handler.CONSOLE.level", logLevel.toUpperCase(Locale.ENGLISH));
        properties.setProperty("handler.CONSOLE.formatter", "COLOR-PATTERN");
        properties.setProperty("handler.CONSOLE.properties", "autoFlush,target,enabled");
        properties.setProperty("handler.CONSOLE.autoFlush", "true");
        properties.setProperty("handler.CONSOLE.target", "SYSTEM_OUT");
        properties.setProperty("handler.CONSOLE.enabled", "true");

        // Configure the handler
        properties.setProperty("handler.FILE", "org.jboss.logmanager.handlers.PeriodicRotatingFileHandler");
        properties.setProperty("handler.FILE.level", logLevel.toUpperCase(Locale.ENGLISH));
        properties.setProperty("handler.FILE.formatter", "PATTERN");
        properties.setProperty("handler.FILE.properties", "autoFlush,append,fileName,enabled");
        properties.setProperty("handler.FILE.constructorProperties", "fileName,append");
        properties.setProperty("handler.FILE.append", "true");
        properties.setProperty("handler.FILE.autoFlush", "true");
        properties.setProperty("handler.FILE.enabled", "true");
        properties.setProperty("handler.FILE.suffix", ".yyyy-MM-dd");
        properties.setProperty("handler.FILE.fileName", logFile);
        
        // Configure the formatter
        properties.setProperty("formatter.PATTERN", "org.jboss.logmanager.formatters.PatternFormatter");
        properties.setProperty("formatter.PATTERN.pattern", "%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n");
        properties.setProperty("formatter.PATTERN.properties", "pattern");
        
        properties.setProperty("formatter.COLOR-PATTERN", "org.jboss.logmanager.formatters.PatternFormatter");
        properties.setProperty("formatter.COLOR-PATTERN.properties", "pattern");
        properties.setProperty("formatter.COLOR-PATTERN.pattern", "%K{level}%d{HH\\:mm\\:ss,SSS} %-5p [%c] (%t) %s%e%n");
        
        return properties;
	}

	private static String getSystemProperty(final String name) {
		return AccessController.doPrivileged(new PrivilegedAction<String>(){

			@Override
			public String run() {
				return System.getProperty(name);
			}});
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
	
	public static void main(String[] args) throws Exception {
		
//		LoggerManagerHelper.configureLogManager("logging.properties");
		
		LoggerManagerHelper.configureLogManager("TRACE", "teiid.log");
		
		Logger logger = Logger.getLogger("org.teiid.COMMAND_LOG");
		System.out.println(logger.isLoggable(Level.FINE));
		
		org.jboss.logmanager.Logger jbossLogger = (org.jboss.logmanager.Logger) logger;
		
		System.out.println(jbossLogger.isLoggable(Level.FINE));
		

		
//		HelloWorld.main(args);
	}
	
	public static final class TeiidLoggerConfigurationLocator implements ConfigurationLocator {

		@Override
		public InputStream findConfiguration() throws IOException {
			final String propLoc = System.getProperty("logging.configuration");
	        if (propLoc != null) try {
	            return new FileInputStream(propLoc);
	        } catch (IOException e) {
	            System.err.printf("Unable to read the logging configuration from '%s' (%s)%n", propLoc, e);
	        }
	        final ClassLoader tccl = Thread.currentThread().getContextClassLoader();
	        if (tccl != null) try {
	            final InputStream stream = tccl.getResourceAsStream(propLoc);
	            if (stream != null) return stream;
	        } catch (Exception e) {
	        }
	        return getClass().getResourceAsStream(propLoc);
		}
	}

}
