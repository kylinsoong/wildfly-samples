package com.customized.tools.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

/**
 * Handler responsible for removing the subsystem resource from the model
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class SubsystemRemove extends AbstractRemoveStepHandler {
	
	private final static Logger log = Logger.getLogger(SubsystemRemove.class);

    static final SubsystemRemove INSTANCE = new SubsystemRemove();


    private SubsystemRemove() {
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
       
    	log.info("SubsystemRemove performBoottime");
    	//Remove any services installed by the corresponding add handler here
        //context.removeService(ServiceName.of("some", "name"));
    }

    @Override
    protected void recoverServices(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        
    	log.info("SubsystemRemove recoverServices");
    	// Restore any service removed in performRuntime by calling the same method the add handler uses.
        SubsystemAdd.installServices(context, operation, model);
    }


}
