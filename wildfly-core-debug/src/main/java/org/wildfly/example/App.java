package org.wildfly.example;

import org.jboss.as.server.Services;
import org.jboss.as.server.mgmt.domain.HostControllerConnectionService;
import org.jboss.msc.service.ServiceName;

public class App {
	
	public static void main(String[] args) {
		
		System.out.println(ServiceName.JBOSS.append("path", "manager"));
		System.out.println(Services.JBOSS_SERVER_EXECUTOR.append("scheduled"));
		System.out.println(HostControllerConnectionService.SERVICE_NAME);
		System.out.println();
	}

}
