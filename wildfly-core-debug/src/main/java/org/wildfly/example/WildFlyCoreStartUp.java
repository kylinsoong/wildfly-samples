package org.wildfly.example;

import org.jboss.as.controller.ControlledProcessState;
import org.jboss.as.controller.ControlledProcessStateService;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceTarget;

public class WildFlyCoreStartUp {

	public static void main(String[] args) {

		ServiceContainer container = ServiceContainer.Factory.create(); 
		final ServiceTarget tracker = container.subTarget();
		final ControlledProcessState processState = new ControlledProcessState(true);
		ControlledProcessStateService.addService(tracker, processState);
		
		container.dumpServices();
	}

}
