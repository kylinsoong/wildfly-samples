package com.customized.tools.extension;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.registry.Resource;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

public class MonitorPersistToFileResourceAdd extends AbstractAddStepHandler {
	
	private final static Logger log = Logger.getLogger(MonitorPersistToFileResourceAdd.class);

	static MonitorPersistToFileResourceAdd INSTANCE = new MonitorPersistToFileResourceAdd();
	
	private MonitorPersistToFileResourceAdd(){
		super(MonitorPersistToFileResource.IS_PERSIST);
	}

	@Override
	protected void performRuntime(OperationContext context,ModelNode operation, ModelNode model)throws OperationFailedException {
		
		boolean isPersist = MonitorPersistToFileResource.IS_PERSIST.resolveModelAttribute(context, model).asBoolean();
		PathAddress address = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.OP_ADDR));
        PathAddress parentAddress = address.subAddress(0, address.size() - 1);
        ModelNode monitorSubsystemModel = Resource.Tools.readModel(context.readResourceFromRoot(parentAddress, true));
        
        log.info("isPersist: " + isPersist + ", address: " + address + ", parentAddress" + parentAddress + ", monitorSubsystemModel: " + monitorSubsystemModel);
	}
}
