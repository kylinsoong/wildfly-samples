package org.jboss.as.quickstarts.cluster.hasingleton.service.ejb;

import javax.ejb.Remote;

/**
 * Business interface to access the SingletonService via this EJB
 *
 */
@Remote
public interface ServiceAccess {
    /**
     * Provide the node name where the scheduler is started.
     *
     * @return name of the cluster node where the schedule timer is running
     */
    String getNodeNameOfTimerService();
}