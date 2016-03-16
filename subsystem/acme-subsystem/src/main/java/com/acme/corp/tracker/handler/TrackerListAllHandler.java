package com.acme.corp.tracker.handler;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.dmr.ModelNode;

public class TrackerListAllHandler implements OperationStepHandler {
    
    public static final TrackerListAllHandler INSTANCE = new TrackerListAllHandler();
    
    private TrackerListAllHandler(){
        
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        // TODO Auto-generated method stub

    }

}
