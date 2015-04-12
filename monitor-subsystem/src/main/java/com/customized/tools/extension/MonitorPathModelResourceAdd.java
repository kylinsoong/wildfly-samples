package com.customized.tools.extension;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.RestartParentResourceAddHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

public class MonitorPathModelResourceAdd extends RestartParentResourceAddHandler{

	private final SimpleAttributeDefinition name;
    private final SimpleAttributeDefinition[] otherAttributes;

	protected MonitorPathModelResourceAdd(SimpleAttributeDefinition name, SimpleAttributeDefinition...otherAttributes) {
		super(ModelDescriptionConstants.SUBSYSTEM);
		this.name = name;
		this.otherAttributes = otherAttributes;
		
	}

	@Override
	protected void populateModel(ModelNode operation, ModelNode model)throws OperationFailedException {
		name.validateAndSet(operation, model);
		if (otherAttributes.length > 0) {
            for (SimpleAttributeDefinition attr : otherAttributes) {
                attr.validateAndSet(operation, model);
            }
        }
	}

	@Override
	protected void recreateParentService(OperationContext context, PathAddress parentAddress, ModelNode model) throws OperationFailedException {
		MonitorSubsystemAdd.installServices(context, model);
	}

	@Override
	protected ServiceName getParentServiceName(PathAddress parentAddress) {
		return ServiceName.JBOSS;
	}
}
