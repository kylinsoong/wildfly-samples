package org.wildfly.hasingleton.service.ejb;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.msc.inject.Injector;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

public class EnvironmentService implements Service<String> {
	
	private static final Logger LOGGER = Logger.getLogger(EnvironmentService.class);
	
	public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("quickstart", "ha", "singleton");
	
	private final AtomicBoolean started = new AtomicBoolean(false);
	
	private String nodeName;
	
	private final InjectedValue<ServerEnvironment> env = new InjectedValue<ServerEnvironment>();
	
	public Injector<ServerEnvironment> getEnvInjector() {
        return this.env;
    }

	public String getValue() throws IllegalStateException, IllegalArgumentException {
		if (!started.get()) {
            throw new IllegalStateException("The service '" + this.getClass().getName() + "' is not ready!");
        }
        return this.nodeName;
	}

	public void start(StartContext context) throws StartException {
		
		if (!started.compareAndSet(false, true)) {
            throw new StartException("The service is still started!");
        }
		LOGGER.info("Start service '" + this.getClass().getName() + "'");
        this.nodeName = this.env.getValue().getNodeName();
	}

	public void stop(StopContext context) {
		if (!started.compareAndSet(true, false)) {
            LOGGER.warn("The service '" + this.getClass().getName() + "' is not active!");
        } else {
            LOGGER.info("Stop service '" + this.getClass().getName() + "'");
        }
	}


}
