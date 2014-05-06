package org.jboss.msc.quickstart.test;

import java.util.HashSet;
import java.util.Set;

import org.jboss.msc.service.AbstractServiceListener;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class ServiceContainerTest {
	
	private ServiceContainer serviceContainer;
	
	public ServiceContainerTest() {
		serviceContainer = ServiceContainer.Factory.create("JBoss MSC Test");
	}
	
	public void testSimpleInstallation() {
		final ServiceBuilder<Void> builder = serviceContainer.addService(ServiceName.of("Test1"), Service.NULL);
		final ServiceController<Void> controller = builder.install();
		final Set<Object> problem = new HashSet<Object>();
        final Set<Object> failed = new HashSet<Object>();
        try {
            serviceContainer.awaitStability(failed, problem);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(controller.getName());
        System.out.println(controller);
        System.out.println(problem);
        System.out.println(failed);
	}
	
	public void testSimpleInstallation2() {
		
		ServiceBuilder<?> builder = serviceContainer.addService(ServiceName.of("Test1"), new Service<Object>(){

			public Object getValue() throws IllegalStateException,IllegalArgumentException {
				return null;
			}

			public void start(StartContext context) throws StartException {
				final ServiceBuilder<Void> builder = context.getChildTarget().addService(ServiceName.of("Test1.child"), NULL);
				builder.addListener(new AbstractServiceListener() {});
				builder.install();
			}

			public void stop(StopContext context) {
				
			}});
		
		builder.addDependencies(ServiceName.of("Test2"));
		final ServiceController<?> controller1 = builder.install();
		builder = serviceContainer.addService(ServiceName.of("Test2"), Service.NULL);
		builder.setInitialMode(ServiceController.Mode.ON_DEMAND);
		final ServiceController<?> controller2 = builder.install();
		final Set<Object> problem = new HashSet<Object>();
        final Set<Object> failed = new HashSet<Object>();
        try {
            serviceContainer.awaitStability(failed, problem);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(controller1.getName());
        System.out.println(controller1);
        System.out.println(controller2.getName());
        System.out.println(controller2);
        System.out.println(problem);
        System.out.println(failed);
	}

	public static void main(String[] args) {
		
		ServiceContainerTest test = new ServiceContainerTest();
		
		test.testSimpleInstallation();
		
//		test.testSimpleInstallation2();
	}

}
