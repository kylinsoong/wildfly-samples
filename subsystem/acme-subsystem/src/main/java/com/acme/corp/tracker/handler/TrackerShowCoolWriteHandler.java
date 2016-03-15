package com.acme.corp.tracker.handler;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;

import com.acme.corp.tracker.extension.TrackerRuntimeService;

public class TrackerShowCoolWriteHandler implements OperationStepHandler {
    
    public static final TrackerShowCoolWriteHandler INSTANCE = new TrackerShowCoolWriteHandler();

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        
        ServiceController<?> controller = context.getServiceRegistry(true).getService(TrackerRuntimeService.NAME);
        if(controller != null) {
            TrackerRuntimeService service = (TrackerRuntimeService) controller.getValue();
            boolean value = operation.get(VALUE).asBoolean();
            System.out.println(value);
            service.setShowCool(value);
            context.getResult().set(true);
        } else {
            context.getResult().set(false);
        }
        
    }

}
