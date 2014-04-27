package org.wildfly.hasingleton.service.ejb;

import java.util.Collection;
import java.util.EnumSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.as.server.ServerEnvironmentService;
import org.jboss.msc.service.AbstractServiceListener;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceController.Transition;
import org.jboss.msc.service.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class StartupSingleton {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupSingleton.class);

	@PostConstruct
	protected void startup() {
		
		LOGGER.info("StartupSingleton will be initialized!");
		
		EnvironmentService service = new EnvironmentService();
		SingletonService<String> singleton = new SingletonService<String>(service, EnvironmentService.SINGLETON_SERVICE_NAME);
		ServiceController<String> controller = singleton.build(CurrentServiceContainer.getServiceContainer()).addDependency(ServerEnvironmentService.SERVICE_NAME, ServerEnvironment.class, service.getEnvInjector()).install();
		controller.setMode(ServiceController.Mode.ACTIVE);
		
		try {
			wait(controller, EnumSet.of(ServiceController.State.DOWN, ServiceController.State.STARTING), ServiceController.State.UP);
			LOGGER.info("StartupSingleton has started the Service");
		} catch (IllegalStateException e) {
			LOGGER.warn("Singleton Service {} not started, are you sure to start in a cluster (HA) environment?",EnvironmentService.SINGLETON_SERVICE_NAME);
		}
		
	}
	
	@PreDestroy
	protected void destroy() {

		LOGGER.info("StartupSingleton will be removed!");
		
		ServiceController<?> controller = CurrentServiceContainer.getServiceContainer().getRequiredService(EnvironmentService.SINGLETON_SERVICE_NAME);
		controller.setMode(ServiceController.Mode.REMOVE);
		
		try {
			wait(controller, EnumSet.of(ServiceController.State.UP, ServiceController.State.STOPPING, ServiceController.State.DOWN), ServiceController.State.REMOVED);
		} catch (IllegalStateException e) {
			LOGGER.warn("Singleton Service {} has not be stopped correctly!",EnvironmentService.SINGLETON_SERVICE_NAME);
		}
	}
	
	private static <T> void wait(ServiceController<T> controller, Collection<ServiceController.State> expectedStates, ServiceController.State targetState) {
		
		if(controller.getState() != targetState) {
			
			ServiceListener<T> listener = new NotifyingServiceListener<T>();
			controller.addListener(listener);
			
			try {
				synchronized (controller) {
					int maxRetry = 2;
					while(expectedStates.contains(controller.getState()) && maxRetry > 0) {
						LOGGER.info("Service controller state is {}, waiting for transition to {}", new Object[] {controller.getState(), targetState});
						controller.wait(5000);
						maxRetry--;
					}
				}
			} catch (InterruptedException e) {
				LOGGER.warn("Wait on startup is interrupted!");
		        Thread.currentThread().interrupt();
			}
			
			controller.removeListener(listener);
			
			ServiceController.State state = controller.getState();
			LOGGER.info("Service controller state is now {}",state);
			if (state != targetState) {
				throw new IllegalStateException(String.format("Failed to wait for state to transition to %s.  Current state is %s", targetState, state), controller.getStartException());
			}
		}
	}
	
	private static class NotifyingServiceListener<T> extends AbstractServiceListener<T> {

		public void transition(ServiceController<? extends T> controller, Transition transition) {
			synchronized(controller){
				controller.notify();
			}
		}
		
	}
}
