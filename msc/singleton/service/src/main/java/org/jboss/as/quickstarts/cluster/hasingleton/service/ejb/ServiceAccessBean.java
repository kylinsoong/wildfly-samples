package org.jboss.as.quickstarts.cluster.hasingleton.service.ejb;

import javax.ejb.Stateless;

import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

/**
 * A simple SLSB to access the internal SingletonService.
 *
 */
@Stateless
public class ServiceAccessBean implements ServiceAccess {
    
	private static final Logger LOGGER = Logger.getLogger(ServiceAccessBean.class);

    public String getNodeNameOfTimerService() {
    	
        LOGGER.info("Method getNodeNameOfTimerService() is invoked");
        
        ServiceController<?> service = CurrentServiceContainer.getServiceContainer().getService(HATimerService.SINGLETON_SERVICE_NAME);

        // Example how to leverage JBoss Logging to do expensive String concatenation only when needed:
        LOGGER.debugf("Service: %s", service);

        if (service != null) {
            return (String) service.getValue();
        } else {
            throw new IllegalStateException("Service '" + HATimerService.SINGLETON_SERVICE_NAME + "' not found!");
        }
    }
}
