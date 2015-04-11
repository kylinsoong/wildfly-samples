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
class MonitorSubsystemRemove extends AbstractRemoveStepHandler {
	
	private final static Logger log = Logger.getLogger(MonitorSubsystemRemove.class);

    static final MonitorSubsystemRemove INSTANCE = new MonitorSubsystemRemove();


    private MonitorSubsystemRemove() {
    }

    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
       
    	log.info("MonitorSubsystemRemove performBoottime");
    	//Remove any services installed by the corresponding add handler here
        //context.removeService(ServiceName.of("some", "name"));
    }

    @Override
    protected void recoverServices(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        
    	log.info("MonitorSubsystemRemove recoverServices");

    }


}
