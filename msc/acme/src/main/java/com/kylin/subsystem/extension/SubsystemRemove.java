package com.kylin.subsystem.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.logging.Logger;

/**
 * Handler responsible for removing the subsystem resource from the model
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class SubsystemRemove extends AbstractRemoveStepHandler {

    static final SubsystemRemove INSTANCE = new SubsystemRemove();

    private final Logger log = Logger.getLogger(SubsystemRemove.class);

    private SubsystemRemove() {
    }

//    @Override
//    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
//        //Remove any services installed by the corresponding add handler here
//        //context.removeService(ServiceName.of("some", "name"));
//    }


}
