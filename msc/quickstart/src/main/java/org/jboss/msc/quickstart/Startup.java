package org.jboss.msc.quickstart;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;

public class Startup {

	public static void main(String[] args) {
		
		ServiceContainer serviceContainer = ServiceContainer.Factory.create();
		Service<MyServiceManager> service = new MyService(new MyServiceManager());
		ServiceBuilder<MyServiceManager> builder = serviceContainer.addService(MyService.SERVICE, service);
		ServiceController<MyServiceManager> controller = builder.install();
		System.out.println(controller.getState());
		System.out.println(controller.getMode());
		System.out.println(controller.getService());
	}

}
