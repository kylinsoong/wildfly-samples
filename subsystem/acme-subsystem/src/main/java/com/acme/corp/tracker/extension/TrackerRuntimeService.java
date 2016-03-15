package com.acme.corp.tracker.extension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistry;
import org.jboss.msc.service.ServiceTarget;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.service.ServiceController.Mode;

public class TrackerRuntimeService implements Service<TrackerRuntimeService> {
    
    public static ServiceName NAME = ServiceName.JBOSS.append("tracker-runtime");
    
    private Set<String> deployments = Collections.synchronizedSet(new HashSet<String>());

    private Set<String> coolDeployments = Collections.synchronizedSet(new HashSet<String>());
    
    private volatile boolean isShowCool = true;
    
    public static TrackerRuntimeService install(ServiceRegistry registry, ServiceTarget target){
        
        ServiceController<?> controller = registry.getService(NAME);
        if(controller == null) {
            target.addService(NAME, new TrackerRuntimeService()).setInitialMode(Mode.ACTIVE).install();
        }      
        return (TrackerRuntimeService) registry.getService(NAME).getValue();
    }

    @Override
    public TrackerRuntimeService getValue() throws IllegalStateException, IllegalArgumentException {
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
    }

    @Override
    public void stop(StopContext context) {
    }

}
