package com.customized.tools.extension;

import java.util.List;

import com.customized.tools.deployment.SubsystemDeploymentProcessor;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.ServiceVerificationHandler;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceController;

class SubsystemAdd extends AbstractBoottimeAddStepHandler {
	
	private final static Logger log = Logger.getLogger(SubsystemAdd.class);

    static final SubsystemAdd INSTANCE = new SubsystemAdd();

    private SubsystemAdd() {
        super(MonitorSubsystemDefinition.ATTRIBUTES);
    }

    static void installServices(OperationContext context, ModelNode operation, ModelNode model) {
    	log.info("SubsystemAdd installServices");
        // Add any services here
        //context.getServiceTarget().addService(ServiceName.of("some", "name"), new MyService()).install();
    }

	@Override
	protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model, ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers) throws OperationFailedException {
		
		log.info("SubsystemAdd performBoottime");
		
		installServices(context, operation, model);
		
		context.addStep(new AbstractDeploymentChainStep() {
            public void execute(DeploymentProcessorTarget processorTarget) {
                processorTarget.addDeploymentProcessor(MonitorExtension.SUBSYSTEM_NAME, SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY, new SubsystemDeploymentProcessor());

            }
        }, OperationContext.Stage.RUNTIME);
	}
}
