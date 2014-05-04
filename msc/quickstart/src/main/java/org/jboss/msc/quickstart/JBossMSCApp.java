package org.jboss.msc.quickstart;

import java.util.concurrent.TimeUnit;

import org.jboss.msc.service.ServiceContainer;

public class JBossMSCApp {

	public static void main(String[] args) {
		ServiceContainer container = ServiceContainer.Factory.create("JBoss MSC App", 2, 10, TimeUnit.MINUTES, false);
		for (int i = 0 ; i < 100 ; i ++) {
		}
	}


}
