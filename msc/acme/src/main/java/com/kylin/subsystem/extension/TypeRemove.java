package com.kylin.subsystem.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

public class TypeRemove extends AbstractRemoveStepHandler{

	public static final TypeRemove INSTANCE = new TypeRemove();
	
	private TypeRemove() {
    }

	protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
		String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
		ServiceName name = TrackerService.createServiceName(suffix);
		context.removeService(name);
	}
}
