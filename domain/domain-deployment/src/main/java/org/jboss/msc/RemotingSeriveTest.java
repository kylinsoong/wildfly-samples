package org.jboss.msc;

import org.jboss.as.remoting.EndpointService;
import org.jboss.as.remoting.EndpointService.EndpointType;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceTarget;

public class RemotingSeriveTest {

	public static void main(String[] args) {

		ServiceContainer serviceContainer = ServiceContainer.Factory.create("test");
		ServiceTarget target = serviceContainer.subTarget();
		EndpointService service = new EndpointService("kylin.redhat.com", EndpointType.MANAGEMENT);
		target.addService(ServiceName.JBOSS.append("test"), service).install();
		
		
	}

}
