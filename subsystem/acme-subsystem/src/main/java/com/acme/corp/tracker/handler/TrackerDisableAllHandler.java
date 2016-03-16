package com.acme.corp.tracker.handler;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.dmr.ModelNode;

import com.acme.corp.tracker.extension.TrackerDeploymentService;

public class TrackerDisableAllHandler implements OperationStepHandler {
    
    public static final TrackerDisableAllHandler INSTANCE = new TrackerDisableAllHandler();
    
    private TrackerDisableAllHandler() { 
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        TrackerDeploymentService service = (TrackerDeploymentService) context.getServiceRegistry(true).getRequiredService(TrackerDeploymentService.NAME).getValue();
        service.setShowCool(false);
        context.getResult().set(true);
    }

}
