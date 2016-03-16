package com.acme.corp.tracker.handler;

import java.util.Set;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.dmr.ModelNode;

import com.acme.corp.tracker.extension.TrackerDeploymentService;

public class TrackerListAllHandler implements OperationStepHandler {
    
    public static final TrackerListAllHandler INSTANCE = new TrackerListAllHandler();
    
    private TrackerListAllHandler(){ 
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {

        TrackerDeploymentService service = (TrackerDeploymentService) context.getServiceRegistry(true).getRequiredService(TrackerDeploymentService.NAME).getValue();
        Set<String> deployments = service.getDeployments();
        final ModelNode result = new ModelNode();
        if(deployments.isEmpty()){
            result.setEmptyList();
        } else {
            for(String deployment : deployments) {
                result.add(deployment);
            }
        }
        context.getResult().set(result);
    }

}
