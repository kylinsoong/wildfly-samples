package com.acme.corp.tracker.handler;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

import com.acme.corp.tracker.extension.TrackerDeploymentService;

/**
 * Handler responsible for removing the subsystem resource from the model
 *
 */
public class SubsystemRemoveHandler extends AbstractRemoveStepHandler {

    public static final SubsystemRemoveHandler INSTANCE = new SubsystemRemoveHandler();

    private final Logger log = Logger.getLogger(SubsystemRemoveHandler.class);

    private SubsystemRemoveHandler() {
    }

    @Override
    protected void performRuntime(OperationContext context,ModelNode operation, ModelNode model)throws OperationFailedException {
        
        log.info("Remove Service: " + TrackerDeploymentService.NAME);
        
        context.removeService(TrackerDeploymentService.NAME);
    }
}
