package com.acme.corp.tracker.handler;

import static com.acme.corp.tracker.extension.TrackerTypeDefinition.TICK_ATTR;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;

import com.acme.corp.tracker.extension.TrackerDeploymentService;
import com.acme.corp.tracker.extension.TrackerService;

public class TypeAddHandler extends AbstractAddStepHandler {
    
    private final Logger log = Logger.getLogger(TypeAddHandler.class);

    public static final TypeAddHandler INSTANCE = new TypeAddHandler();

    private TypeAddHandler() {
    }

   
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        TICK_ATTR.validateAndSet(operation,model);
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        
        String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        long tick = TICK_ATTR.resolveModelAttribute(context,model).asLong();
        TrackerService service = new TrackerService(suffix, tick);
        ServiceName name = TrackerService.createServiceName(suffix);
        
        log.info("Add a new service: " + name);
        
        context.getServiceTarget()
               .addService(name, service)
               .addDependency(TrackerDeploymentService.NAME, TrackerDeploymentService.class, service.getDeploymentService())
               .setInitialMode(Mode.ACTIVE)
               .install();
    }
}
