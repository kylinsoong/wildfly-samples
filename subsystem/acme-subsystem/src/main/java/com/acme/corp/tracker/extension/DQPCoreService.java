package com.acme.corp.tracker.extension;

import javax.transaction.TransactionManager;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

public class DQPCoreService implements Service<DQPCoreService> {
    
    public static final ServiceName serviceName = ServiceName.JBOSS.append("tracker", "DQPCore");
    
    private final InjectedValue<TransactionManager> txnManagerInjector = new InjectedValue<TransactionManager>();

    @Override
    public DQPCoreService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }

    @Override
    public void start(StartContext context) throws StartException {
        
        TransactionManager tm = getTxnManagerInjector().getValue();
        
        System.out.println(tm);
    }

    @Override
    public void stop(StopContext context) {
        
    }
    
    public InjectedValue<TransactionManager> getTxnManagerInjector() {
        return txnManagerInjector;
    }

}
