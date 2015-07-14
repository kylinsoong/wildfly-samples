package org.wildfly.example.logging;

import java.util.logging.LogManager;

public class LogManagerDebug {

	public static void main(String[] args) {

		final LogManager logManager = LogManager.getLogManager();
		
		System.out.println(logManager);
	}

}
