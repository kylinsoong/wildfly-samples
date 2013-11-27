package org.jboss.as.quickstarts.cluster.hasingleton.service.ejb;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.as.server.ServerEnvironment;
import org.jboss.logging.Logger;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;


/**
 * A service to start schedule-timer as HASingleton timer in a clustered environment.
 * The service will ensure that the timer is initialized only once in a cluster.
 *
 */
public class HATimerService implements Service<String> {
    
	private static final Logger LOGGER = Logger.getLogger(HATimerService.class);
    
	public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("quickstart", "ha", "singleton", "timer");

    /**
     * A flag whether the service is started.
     */
    private final AtomicBoolean started = new AtomicBoolean(false);

    private String nodeName;

    final InjectedValue<ServerEnvironment> env = new InjectedValue<ServerEnvironment>();

    /**
     * @return the name of the server node
     */
    public String getValue() throws IllegalStateException, IllegalArgumentException {
        if (!started.get()) {
            throw new IllegalStateException("The service '" + this.getClass().getName() + "' is not ready!");
        }
        return this.nodeName;
    }

    public void start(StartContext arg0) throws StartException {
    	
        if (!started.compareAndSet(false, true)) {
            throw new StartException("The service is still started!");
        }
        LOGGER.info("Start HASingleton timer service '" + this.getClass().getName() + "'");

        this.nodeName = this.env.getValue().getNodeName();

        try {
            InitialContext ic = new InitialContext();
            ((Scheduler) ic.lookup("global/jboss-as-cluster-ha-singleton-service/SchedulerBean!org.jboss.as.quickstarts.cluster.hasingleton.service.ejb.Scheduler")).initialize("HASingleton timer @" + this.nodeName + " " + new Date());
        } catch (NamingException e) {
            throw new StartException("Could not initialize timer", e);
        }
    }

    public void stop(StopContext arg0) {
        if (!started.compareAndSet(true, false)) {
            LOGGER.warn("The service '" + this.getClass().getName() + "' is not active!");
        } else {
            LOGGER.info("Stop HASingleton timer service '" + this.getClass().getName() + "'");
            try {
                InitialContext ic = new InitialContext();
                ((Scheduler) ic.lookup("global/jboss-as-cluster-ha-singleton-service/SchedulerBean!org.jboss.as.quickstarts.cluster.hasingleton.service.ejb.Scheduler")).stop();
            } catch (NamingException e) {
                LOGGER.error("Could not stop timer", e);
            }
        }
    }
}
