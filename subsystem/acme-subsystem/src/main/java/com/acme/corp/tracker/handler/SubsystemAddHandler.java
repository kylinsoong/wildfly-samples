package com.acme.corp.tracker.handler;

import static com.acme.corp.tracker.extension.TrackerExtension.SUBSYSTEM_NAME;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController.Mode;

import com.acme.corp.tracker.deployment.SubsystemDeploymentProcessor;
import com.acme.corp.tracker.extension.TrackerDeploymentService;

/**
 * Handler responsible for adding the subsystem resource to the model
 *
 */
public class SubsystemAddHandler extends AbstractBoottimeAddStepHandler {

    public static final SubsystemAddHandler INSTANCE = new SubsystemAddHandler();

    private final Logger log = Logger.getLogger(SubsystemAddHandler.class);

    private SubsystemAddHandler() {
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        super.populateModel(operation, model);
    }


    @Override
    protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model)throws OperationFailedException {
                
        log.info("Add a deployer hook, add a new service: " + TrackerDeploymentService.NAME);
        
        // Add a hook into deployers, which subsystem will be noticed once a deployment be deploy 
        context.addStep(new AbstractDeploymentChainStep() {
            public void execute(DeploymentProcessorTarget processorTarget) {
                processorTarget.addDeploymentProcessor(SUBSYSTEM_NAME, SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY, new SubsystemDeploymentProcessor());
            }
        }, OperationContext.Stage.RUNTIME);
        
        // Add TrackerRuntimeService
        context.getServiceTarget()
               .addService(TrackerDeploymentService.NAME, new TrackerDeploymentService())
               .setInitialMode(Mode.ACTIVE)
               .install();
        
    }
}
