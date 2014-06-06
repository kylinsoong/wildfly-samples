package org.wildfly.domain.test;

import java.util.concurrent.TimeUnit;

import org.jboss.as.controller.ControlledProcessState;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceTarget;

public class StandaloneStartMSC {

	public static void main(String[] args) {

		ServiceContainer container = ServiceContainer.Factory.create("jboss-as", 8, 30, TimeUnit.SECONDS);
	
		ServiceTarget tracker = container.subTarget();
		
//		final ControlledProcessState processState = new ControlledProcessState(configuration.getServerEnvironment().isStandalone());
		
		System.out.println(tracker);
		
	}

}
