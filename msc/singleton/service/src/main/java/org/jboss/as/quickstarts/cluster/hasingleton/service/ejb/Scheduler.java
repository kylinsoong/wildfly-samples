package org.jboss.as.quickstarts.cluster.hasingleton.service.ejb;

public interface Scheduler {

    void initialize(String info);

    void stop();

}