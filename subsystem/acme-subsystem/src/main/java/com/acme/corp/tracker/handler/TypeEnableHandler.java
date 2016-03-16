package com.acme.corp.tracker.handler;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

import com.acme.corp.tracker.extension.TrackerService;

public class TypeEnableHandler implements OperationStepHandler {
    
    public static final TypeEnableHandler INSTANCE = new TypeEnableHandler();
    
    private TypeEnableHandler() {
        
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        final String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        TrackerService service = (TrackerService) context.getServiceRegistry(true).getRequiredService(TrackerService.createServiceName(suffix)).getValue();
        service.setShowCool(true);
        context.getResult().set(true);
    }

}
