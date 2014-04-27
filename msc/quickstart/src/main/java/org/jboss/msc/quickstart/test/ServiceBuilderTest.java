package org.jboss.msc.quickstart.test;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceBuilder.DependencyType;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class ServiceBuilderTest {
	
	private final ServiceName serviceName = ServiceName.of("service");
	private final ServiceName dummyServiceName = ServiceName.of("dummy", "service");
	private final ServiceName anotherServiceName = ServiceName.of("any", "other", "dummy", "service");
	
	private ServiceContainer serviceContainer;
	
	public ServiceBuilderTest() {
		serviceContainer = ServiceContainer.Factory.create();
	}
	
	public void addServiceWithoutInjection() {
		
		final DummyManager dummyManager = new DummyManager();
		final Service<DummyManager> service = new DummyService(dummyManager);
		
		ServiceBuilder<?> serviceBuilder = serviceContainer.addService(serviceName, service);
		serviceBuilder.addAliases(dummyServiceName);
		serviceBuilder.addDependencies(DependencyType.REQUIRED, anotherServiceName);
		ServiceController<?> dummyController = serviceBuilder.install();
	}

	public static void main(String[] args) {
		ServiceBuilderTest test = new ServiceBuilderTest();
		
		test.addServiceWithoutInjection();
	}
	
	private static final class DummyService implements Service<DummyManager> {
		
		private DummyManager dummyManager;

		public DummyService(DummyManager dummyManager) {
			this.dummyManager = dummyManager;
		}

		public DummyManager getValue() throws IllegalStateException,IllegalArgumentException {
			return dummyManager;
		}

		public void start(StartContext context) throws StartException {
			dummyManager.initialize("init");
		}

		public void stop(StopContext context) {
			dummyManager.cleanup();
		}
		
	}
	
	public static final class DummyManager {
		
		private String description;
		
		private DummyHelper helper;
		
		public void initialize(String description) {
            this.description = description;
        }
		
		public void cleanup() {
            this.description = null;
        }

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public DummyHelper getHelper() {
			return helper;
		}

		public void setHelper(DummyHelper helper) {
			this.helper = helper;
		}
		
	}
	
	public static final class DummyHelper {}

}
