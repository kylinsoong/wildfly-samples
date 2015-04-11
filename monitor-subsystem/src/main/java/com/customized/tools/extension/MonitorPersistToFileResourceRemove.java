package com.customized.tools.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

public class MonitorPersistToFileResourceRemove extends AbstractRemoveStepHandler {
	
	private final static Logger log = Logger.getLogger(MonitorPersistToFileResourceRemove.class);
	
	static MonitorPersistToFileResourceRemove INSTANCE = new MonitorPersistToFileResourceRemove();

	private MonitorPersistToFileResourceRemove() {
		
	}

	@Override
	protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
		log.info("MonitorPersistToFileResourceRemove performBoottime, operation: " + operation + ", model: " + model);
	}

	@Override
	protected void recoverServices(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
		MonitorPersistToFileResourceAdd.INSTANCE.performRuntime(context, operation, model);
	}
	
	
}
