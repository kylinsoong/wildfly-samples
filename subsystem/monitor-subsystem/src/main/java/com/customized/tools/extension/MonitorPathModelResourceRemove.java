package com.customized.tools.extension;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.RestartParentResourceRemoveHandler;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

public class MonitorPathModelResourceRemove extends RestartParentResourceRemoveHandler{

	protected MonitorPathModelResourceRemove() {
		super(ModelDescriptionConstants.SUBSYSTEM);
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
