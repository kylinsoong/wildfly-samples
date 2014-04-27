package org.jboss.msc.quickstart;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class MyService implements Service<MyServiceManager> {
	
	final static ServiceName SERVICE = ServiceName.of("service");
	
	private MyServiceManager manager;
	
	public MyService(MyServiceManager manager) {
		this.manager = manager;
	}

	public MyServiceManager getValue() throws IllegalStateException, IllegalArgumentException {
		return manager;
	}

	public void start(StartContext context) throws StartException {
		manager.initialize("init");
	}

	public void stop(StopContext context) {
		manager.cleanup();
	}

}
