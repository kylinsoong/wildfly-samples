package com.customized.tools.extension;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

class MonitorSubsystemAdd extends AbstractBoottimeAddStepHandler {
	
	private final static Logger log = Logger.getLogger(MonitorSubsystemAdd.class);

    static final MonitorSubsystemAdd INSTANCE = new MonitorSubsystemAdd();

    private MonitorSubsystemAdd() {
        super(MonitorSubsystemDefinition.ATTRIBUTES);
        log.info("SubsystemAdd constructor");
    }

    static void installServices(OperationContext context, ModelNode operation, ModelNode model) {
    	log.info("SubsystemAdd installServices");
        // Add any services here
        //context.getServiceTarget().addService(ServiceName.of("some", "name"), new MyService()).install();
    }
    
    @Override
    protected void performRuntime(final OperationContext context, final ModelNode operation, final ModelNode model) throws OperationFailedException {
    	log.info("SubsystemAdd performBoottime, operation: " + operation + ", model: " + model);
    }

//	@Override
//	protected void performBoottime(OperationContext context, ModelNode operation, ModelNode model, ServiceVerificationHandler verificationHandler, List<ServiceController<?>> newControllers) throws OperationFailedException {
//		
//		log.info("SubsystemAdd performBoottime");
//		
//		installServices(context, operation, model);
//		
//		context.addStep(new AbstractDeploymentChainStep() {
//            public void execute(DeploymentProcessorTarget processorTarget) {
//                processorTarget.addDeploymentProcessor(MonitorExtension.SUBSYSTEM_NAME, SubsystemDeploymentProcessor.PHASE, SubsystemDeploymentProcessor.PRIORITY, new SubsystemDeploymentProcessor());
//
//            }
//        }, OperationContext.Stage.RUNTIME);
//	}
}
