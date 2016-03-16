package com.acme.corp.tracker.handler;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;

import com.acme.corp.tracker.extension.TrackerDeploymentService;
import com.acme.corp.tracker.extension.TrackerSubsystemDefinition;

public class TrackerShowCoolReadHandler implements OperationStepHandler {
    
    public static final TrackerShowCoolReadHandler INSTANCE = new TrackerShowCoolReadHandler();
    
    private TrackerShowCoolReadHandler() {
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        
        ServiceController<?> controller = context.getServiceRegistry(true).getService(TrackerDeploymentService.NAME);
        if(controller != null) {
            TrackerDeploymentService service = (TrackerDeploymentService) controller.getValue();
            context.getResult().set(service.isShowCool());
        } else {
            context.getResult().set(TrackerSubsystemDefinition.SHOW_COOL_DEPLOYMENTS.getDefaultValue());
        }        
    }

}
