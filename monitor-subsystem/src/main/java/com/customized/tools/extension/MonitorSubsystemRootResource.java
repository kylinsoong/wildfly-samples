package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.OperationContext.Stage;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.Resource;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.logging.Logger;


public class MonitorSubsystemRootResource extends SimpleResourceDefinition {
	
	private final Logger log = Logger.getLogger(MonitorSubsystemRootResource.class);
	
	private static final SimpleAttributeDefinition FOLDERPATH_MODEL_ALIAS = SimpleAttributeDefinitionBuilder.create(CommonAttributes.FOLDERPATH_MODEL, ModelType.BOOLEAN, true)
																			.addFlag(AttributeAccess.Flag.ALIAS)
																            .build();
	
	private static final SimpleAttributeDefinition RESULTFILE_MODEL_ALIAS = SimpleAttributeDefinitionBuilder.create(CommonAttributes.RESULTFILE_MODEL, ModelType.BOOLEAN, true)
																			.addFlag(AttributeAccess.Flag.ALIAS)
																            .build();
	
	private static final SimpleAttributeDefinition PERSIST_MODEL_ALIAS = SimpleAttributeDefinitionBuilder.create(CommonAttributes.PERSIST_MODEL, ModelType.BOOLEAN, true)
																			.addFlag(AttributeAccess.Flag.ALIAS)
																            .build();
	
	public static final PathElement PATH_ELEMENT = PathElement.pathElement(ModelDescriptionConstants.SUBSYSTEM, MonitorExtension.SUBSYSTEM_NAME);
	

    static final MonitorSubsystemRootResource INSTANCE = new MonitorSubsystemRootResource();

    private MonitorSubsystemRootResource() {
        super(PATH_ELEMENT, 
        	  MonitorExtension.getResourceDescriptionResolver(MonitorExtension.SUBSYSTEM_NAME), 
        	  MonitorSubsystemAdd.INSTANCE, 
        	  MonitorSubsystemRemove.INSTANCE);
        log.info("MonitorSubsystemDefinition constructor");
    }
    
    @Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {   	
    	resourceRegistration.registerReadWriteAttribute(FOLDERPATH_MODEL_ALIAS, FolderPathModelAliasReadHandler.INSTANCE, FolderPathModelAliasWriteHandler.INSTANCE);
    	resourceRegistration.registerReadWriteAttribute(RESULTFILE_MODEL_ALIAS, FilePathModelAliasReadHandler.INSTANCE, FilePathModelAliasWriteHandler.INSTANCE);
    	resourceRegistration.registerReadWriteAttribute(PERSIST_MODEL_ALIAS, PersistModelAliasReadHandler.INSTANCE, PersistModelAliasWriteHandler.INSTANCE);
    	
    	log.info("registerReadWriteAttribute FOLDERPATH_MODEL_ALIAS, RESULTFILE_MODEL_ALIAS, PERSIST_MODEL_ALIAS");
	}

	@Override
	public void registerOperations(ManagementResourceRegistration resourceRegistration) {
		super.registerOperations(resourceRegistration);
		resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
		
		log.info("register Operations ");
	}

	@Override
	public void registerChildren(ManagementResourceRegistration resourceRegistration) {
		resourceRegistration.registerSubModel(new MonitorFileNameModelResource());
		resourceRegistration.registerSubModel(new MonitorFolderPathModelResource());
		resourceRegistration.registerSubModel(new MonitorPersistToFileResource());
		
		log.info("register Children ");
	}
	
	private static class FolderPathModelAliasReadHandler implements OperationStepHandler{

		static final FolderPathModelAliasReadHandler INSTANCE = new FolderPathModelAliasReadHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final Resource resource = context.readResource(PathAddress.EMPTY_ADDRESS);
			context.getResult().set(resource.hasChild(MonitorFolderPathModelResource.PATH_ELEMENT));
		}
		
	}
	
	private static class FolderPathModelAliasWriteHandler implements OperationStepHandler{

		static final FolderPathModelAliasWriteHandler INSTANCE = new FolderPathModelAliasWriteHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final boolean value = operation.get(ModelDescriptionConstants.VALUE).asBoolean(false);
            boolean hasResource = context.readResource(PathAddress.EMPTY_ADDRESS).hasChild(MonitorFolderPathModelResource.PATH_ELEMENT);
            if (value) {
                if (!hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorFolderPathModelResource.PATH_ELEMENT), ADD).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(ADD);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorFolderPathModelResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            } else {
                if (hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorFolderPathModelResource.PATH_ELEMENT), REMOVE).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(REMOVE);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorFolderPathModelResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            }
		}
	}
	
	private static class FilePathModelAliasReadHandler implements OperationStepHandler{

		static final FilePathModelAliasReadHandler INSTANCE = new FilePathModelAliasReadHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final Resource resource = context.readResource(PathAddress.EMPTY_ADDRESS);
			context.getResult().set(resource.hasChild(MonitorFileNameModelResource.PATH_ELEMENT));
		}
		
	}
	
	private static class FilePathModelAliasWriteHandler implements OperationStepHandler{

		static final FilePathModelAliasWriteHandler INSTANCE = new FilePathModelAliasWriteHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final boolean value = operation.get(ModelDescriptionConstants.VALUE).asBoolean(false);
            boolean hasResource = context.readResource(PathAddress.EMPTY_ADDRESS).hasChild(MonitorFileNameModelResource.PATH_ELEMENT);
            if (value) {
                if (!hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorFileNameModelResource.PATH_ELEMENT), ADD).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(ADD);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorFileNameModelResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            } else {
                if (hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorFileNameModelResource.PATH_ELEMENT), REMOVE).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(REMOVE);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorFileNameModelResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            }
		}
	}
	
	private static class PersistModelAliasReadHandler implements OperationStepHandler{

		static final PersistModelAliasReadHandler INSTANCE = new PersistModelAliasReadHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final Resource resource = context.readResource(PathAddress.EMPTY_ADDRESS);
			context.getResult().set(resource.hasChild(MonitorPersistToFileResource.PATH_ELEMENT));
		}
		
	}
	
	private static class PersistModelAliasWriteHandler implements OperationStepHandler{

		static final PersistModelAliasWriteHandler INSTANCE = new PersistModelAliasWriteHandler();
		@Override
		public void execute(OperationContext context, ModelNode operation)throws OperationFailedException {
			final boolean value = operation.get(ModelDescriptionConstants.VALUE).asBoolean(false);
            boolean hasResource = context.readResource(PathAddress.EMPTY_ADDRESS).hasChild(MonitorPersistToFileResource.PATH_ELEMENT);
            if (value) {
                if (!hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorPersistToFileResource.PATH_ELEMENT), ADD).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(ADD);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorPersistToFileResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            } else {
                if (hasResource) {
                    OperationStepHandler handler = context.getResourceRegistration().getOperationEntry(PathAddress.pathAddress(MonitorPersistToFileResource.PATH_ELEMENT), REMOVE).getOperationHandler();
                    ModelNode addOp = new ModelNode();
                    addOp.get(OP).set(REMOVE);
                    addOp.get(OP_ADDR).set(PathAddress.pathAddress(operation.get(OP_ADDR)).append(MonitorPersistToFileResource.PATH_ELEMENT).toModelNode());
                    context.addStep(addOp, handler, Stage.MODEL, true);
                }
            }
		}
	}

	


}
