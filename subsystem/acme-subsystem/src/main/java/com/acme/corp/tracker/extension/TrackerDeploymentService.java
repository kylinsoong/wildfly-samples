package com.acme.corp.tracker.extension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class TrackerDeploymentService implements Service<TrackerDeploymentService> {
    
    public static ServiceName NAME = ServiceName.JBOSS.append("tracker-deployment");
    
    private Set<String> deployments;

    private Set<String> coolDeployments;
    
    private volatile boolean isShowCool;
    
    @Override
    public TrackerDeploymentService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    public Set<String> getDeployments() {
        return deployments;
    }

    public void addDeployments(String deployment) {
        this.deployments.add(deployment);
    }

    public Set<String> getCoolDeployments() {
        return coolDeployments;
    }

    public void addCoolDeployments(String deployment) {
        this.coolDeployments.add(deployment);
    }

    public boolean isShowCool() {
        return isShowCool;
    }

    public void setShowCool(boolean isShowCool) {
        this.isShowCool = isShowCool;
    }

    @Override
    public void start(StartContext context) throws StartException {
        isShowCool = true;
        deployments = Collections.synchronizedSet(new HashSet<String>());
        coolDeployments = Collections.synchronizedSet(new HashSet<String>());
    }

    @Override
    public void stop(StopContext context) {
        isShowCool = false;
        deployments.clear();
        coolDeployments.clear();    
    }

}
