package com.acme.corp.tracker.handler;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;

import com.acme.corp.tracker.extension.TrackerService;

public class TypeTickReadHandler implements OperationStepHandler{
    
    public static final TypeTickReadHandler INSTANCE = new TypeTickReadHandler();
    
    private TypeTickReadHandler(){
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        final String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        ServiceName serviceName = TrackerService.createServiceName(suffix);
        ServiceController<?> controller = context.getServiceRegistry(true).getService(serviceName);
        if(controller != null) {
            TrackerService service = (TrackerService)controller.getValue();
            context.getResult().set(service.getTick());
        } 
    }

}
