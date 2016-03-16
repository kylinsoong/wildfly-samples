package com.acme.corp.tracker.handler;

import org.jboss.as.controller.AbstractWriteAttributeHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationContext.RollbackHandler;
import org.jboss.dmr.ModelNode;

import com.acme.corp.tracker.extension.TrackerDeploymentService;
import com.acme.corp.tracker.extension.TrackerSubsystemDefinition;

public class TrackerShowCoolWriteHandler extends AbstractWriteAttributeHandler<Void>{
    
    public static final TrackerShowCoolWriteHandler INSTANCE = new TrackerShowCoolWriteHandler();
    
    private TrackerShowCoolWriteHandler(){
        
    }

    @Override
    protected boolean applyUpdateToRuntime(
            OperationContext context,
            ModelNode operation,
            String attributeName,
            ModelNode resolvedValue,
            ModelNode currentValue,
            org.jboss.as.controller.AbstractWriteAttributeHandler.HandbackHolder<Void> handbackHolder)
            throws OperationFailedException {
        
        if (attributeName.equals(TrackerSubsystemDefinition.SHOW_COOL)){
            TrackerDeploymentService service = (TrackerDeploymentService) context.getServiceRegistry(true).getRequiredService(TrackerDeploymentService.NAME).getValue();
            service.setShowCool(resolvedValue.asBoolean());
            context.getResult().set(true);
            context.completeStep(RollbackHandler.NOOP_ROLLBACK_HANDLER);
        }
        
        return false;
    }

    @Override
    protected void revertUpdateToRuntime(OperationContext context, ModelNode operation, String attributeName, ModelNode valueToRestore, ModelNode valueToRevert, Void handback) throws OperationFailedException {        
    }

}
