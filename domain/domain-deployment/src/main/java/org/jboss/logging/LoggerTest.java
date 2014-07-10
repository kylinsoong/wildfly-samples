package org.jboss.logging;

import org.jboss.as.host.controller.HostControllerLogger;

public class LoggerTest {

	public static void main(String[] args) {
		
		System.out.println(HostControllerLogger.class.getPackage().getName());

		HostControllerLogger.ROOT_LOGGER.tracef("trying to reconnect to %s current-state (%s) required-state (%s)", "jo004006", "SERVER_STARTED", "SERVER_STARTED");
	}

}
