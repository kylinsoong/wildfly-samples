package com.customized.tools.extension;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

class MonitorSubsystemAdd extends AbstractAddStepHandler {
	
	private final static Logger log = Logger.getLogger(MonitorSubsystemAdd.class);

    static final MonitorSubsystemAdd INSTANCE = new MonitorSubsystemAdd();

    private MonitorSubsystemAdd() {
    	super();
        log.info("MonitorSubsystemAdd constructor");
    }

    static void installServices(OperationContext context, ModelNode model) {
    	log.info("MonitorSubsystemAdd installServices");

    }
    
    @Override
    protected void performRuntime(final OperationContext context, final ModelNode operation, final ModelNode model) throws OperationFailedException {
    	log.info("MonitorSubsystemAdd performBoottime, operation: " + operation + ", model: " + model);
    	installServices(context, model);
    }


}
