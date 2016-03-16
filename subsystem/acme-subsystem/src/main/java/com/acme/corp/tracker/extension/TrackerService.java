package com.acme.corp.tracker.extension;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class TrackerService implements Service<TrackerService> {
    
    private final InjectedValue<TrackerDeploymentService> deploymentService = new InjectedValue<TrackerDeploymentService>();
    
    private AtomicLong tick = new AtomicLong(10000);

    private Set<String> deployments = Collections.synchronizedSet(new HashSet<String>());

    private Set<String> coolDeployments = Collections.synchronizedSet(new HashSet<String>());

    private final String suffix;
    
    private boolean isShowCool = true;

    private Thread OUTPUT = new Thread() {
        @Override
        public void run() {
            while (true) {
                Thread.currentThread().setName("tracker-" + suffix + "-thread");
                try {
                    Thread.sleep(tick.get());
                    while(isShowCool() && deploymentService.getValue().getValue().isShowCool()){
                        System.out.println("Current deployments deployed while " + suffix + " tracking active:" + deployments + ", Cool: " + coolDeployments.size());
                        Thread.sleep(tick.get());
                    }
                } catch (InterruptedException e) {
                    interrupted();
                    break;
                }
            }
        }
    };

    public TrackerService(String suffix, long tick) {
        this.suffix = suffix;
        this.tick.set(tick);
    }

    public boolean isShowCool() {
        return isShowCool;
    }

    public void setShowCool(boolean isShowCool) {
        this.isShowCool = isShowCool;
    }

    public InjectedValue<TrackerDeploymentService> getDeploymentService() {
        return deploymentService;
    }

    @Override
    public TrackerService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    @Override
    public void start(StartContext context) throws StartException {
        OUTPUT.start();
    }

    @Override
    public void stop(StopContext context) {
        OUTPUT.interrupt();
    }

    public static ServiceName createServiceName(String suffix) {
        return ServiceName.JBOSS.append("tracker", suffix);
    }

    public void addDeployment(String name) {
        deployments.add(name);
        deploymentService.getValue().addDeployments(name);
    }

    public void addCoolDeployment(String name) {
        coolDeployments.add(name);
        deploymentService.getValue().addCoolDeployments(name);
    }

    public void removeDeployment(String name) {
        deployments.remove(name);
        coolDeployments.remove(name);
        deploymentService.getValue().getDeployments().remove(name);
        deploymentService.getValue().getCoolDeployments().remove(name);
    }

    public void setTick(long tick) {
        this.tick.set(tick);
    }

    public long getTick() {
        return this.tick.get();
    }
}
