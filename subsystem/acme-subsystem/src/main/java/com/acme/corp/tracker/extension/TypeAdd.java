package com.acme.corp.tracker.extension;

import static com.acme.corp.tracker.extension.TypeDefinition.TICK;


import java.util.List;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceName;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class TypeAdd extends AbstractAddStepHandler {

    public static final TypeAdd INSTANCE = new TypeAdd();

    private TypeAdd() {
    }

   
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        TICK.validateAndSet(operation,model);
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model,
            ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers)
            throws OperationFailedException {
        String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        long tick = TICK.resolveModelAttribute(context,model).asLong();
        TrackerService service = new TrackerService(suffix, tick);
        ServiceName name = TrackerService.createServiceName(suffix);
        ServiceController<TrackerService> controller = context.getServiceTarget()
                .addService(name, service)
                .addListener(verificationHandler)
                .setInitialMode(Mode.ACTIVE)
                .install();
        newControllers.add(controller);
        
//        DQPCoreService dqp = new DQPCoreService();
//        ServiceController<Void> controller2 = context.getServiceTarget()
//                .addService(DQPCoreService.serviceName, dqp)
//                .setInitialMode(Mode.ACTIVE)
//                .install();
//        newControllers.add(controller2);
    }
}
