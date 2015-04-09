package com.customized.tools.extension;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.RestartParentResourceAddHandler;
import org.jboss.as.controller.RestartParentResourceRemoveHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.descriptions.ResourceDescriptionResolver;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceName;

public class MonitorModelResource extends SimpleResourceDefinition{

	public MonitorModelResource(PathElement pathElement, ResourceDescriptionResolver descriptionResolver, SimpleAttributeDefinition name, SimpleAttributeDefinition...otherAttributes) {
		super(pathElement, descriptionResolver, new ShowModelAdd(name, otherAttributes), new ShowModelRemove());
		// TODO Auto-generated constructor stub
	}
	
	private static class ShowModelAdd extends RestartParentResourceAddHandler {
		
		private final SimpleAttributeDefinition name;
        private final SimpleAttributeDefinition[] otherAttributes;

		protected ShowModelAdd(SimpleAttributeDefinition name, SimpleAttributeDefinition...otherAttributes) {
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
	
	 private static class ShowModelRemove extends RestartParentResourceRemoveHandler {

		protected ShowModelRemove() {
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

}
