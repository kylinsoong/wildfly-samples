package com.acme.corp.tracker.extension;

import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

public class TrackerTickHandler extends AbstractWriteAttributeHandler<Void> {

	public static final TrackerTickHandler INSTANCE = new TrackerTickHandler();
	 
    private TrackerTickHandler() {
        super(TypeDefinition.TICK);
    }
 
    protected boolean applyUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName,
              ModelNode resolvedValue, ModelNode currentValue, HandbackHolder<Void> handbackHolder) throws OperationFailedException {
 
        modifyTick(context, operation, resolvedValue.asLong());
 
        return false;
    }
 
    protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode valueToRestore, ModelNode valueToRevert, Void handback){
        modifyTick(context, operation, valueToRestore.asLong());
    }
 
    private void modifyTick(OperationContext context, ModelNode operation, long value) throws OperationFailedException {
 
        final String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        TrackerService service = (TrackerService) context.getServiceRegistry(true).getRequiredService(TrackerService.createServiceName(suffix)).getValue();
        service.setTick(value);
    }

	@Override
	protected void revertUpdateToRuntime(OperationContext context,
			ModelNode operation, String attributeName,
			ModelNode valueToRestore, ModelNode valueToRevert, Void handback)
			throws OperationFailedException {
		// TODO Auto-generated method stub
		
	}

}
