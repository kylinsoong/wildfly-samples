package com.acme.corp.tracker.extension;

import static com.acme.corp.tracker.extension.TrackerExtension.SUBSYSTEM_NAME;

import java.util.List;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

import com.acme.corp.tracker.deployment.SubsystemDeploymentProcessor;

/**
 * Handler responsible for adding the subsystem resource to the model
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class SubsystemAddHandler extends AbstractBoottimeAddStepHandler {

    static final SubsystemAddHandler INSTANCE = new SubsystemAddHandler();

    private final Logger log = Logger.getLogger(SubsystemAddHandler.class);

    private SubsystemAddHandler() {
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {

    }

    /** {@inheritDoc} */
    @Override
    public void performBoottime(OperationContext context, 
                                ModelNode operation, 
                                ModelNode model,
                                ServiceVerificationHandler verificationHandler, 
                                List<ServiceController<?>> newControllers) throws OperationFailedException {
        
        log.info("Populating the model");

        //Add deployment processors here
        //Remove this if you don't need to hook into the deployers, or you can add as many as you like
        //see SubDeploymentProcessor for explanation of the phases
        context.addStep(new AbstractDeploymentChainStep() {
            public void execute(DeploymentProcessorTarget processorTarget) {
                processorTarget.addDeploymentProcessor(SUBSYSTEM_NAME, SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY, new SubsystemDeploymentProcessor());

            }
        }, OperationContext.Stage.RUNTIME);

    }
}
