package org.wildfly.hasingleton.service.ejb;

import javax.ejb.Stateless;

import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ServiceAccessBean implements ServiceAccess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAccessBean.class);

	public String getNodeNameOfService() {
		
		LOGGER.info("getNodeNameOfService() is called()");
		
		ServiceController<?> service = CurrentServiceContainer.getServiceContainer().getService(EnvironmentService.SINGLETON_SERVICE_NAME);
		
		if (service != null){
			return (String) service.getValue();
		} else {
			throw new IllegalStateException("Service '" + EnvironmentService.SINGLETON_SERVICE_NAME + "' not found!");
		}
		
	}

}
