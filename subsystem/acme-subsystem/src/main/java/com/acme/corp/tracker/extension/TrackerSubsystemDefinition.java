package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.acme.corp.tracker.handler.TrackerShowCoolReadHandler;
import com.acme.corp.tracker.handler.TrackerShowCoolWriteHandler;

public class TrackerSubsystemDefinition extends SimpleResourceDefinition {
    
    static String SHOW_COOL = "show-cool-deployments";

    protected TrackerSubsystemDefinition() {
        super(TrackerExtension.SUBSYSTEM_PATH, TrackerExtension.getResourceDescriptionResolver(null), SubsystemAddHandler.INSTANCE, SubsystemRemoveHandler.INSTANCE);
    }
    
    protected TrackerSubsystemDefinition(Parameters parameters){
        super(parameters);
    }
    
    public static final SimpleAttributeDefinition SHOW_COOL_DEPLOYMENTS = SimpleAttributeDefinitionBuilder.create(SHOW_COOL, ModelType.BOOLEAN, true)
            .addFlag(AttributeAccess.Flag.RESTART_NONE)
            .setDefaultValue(new ModelNode(true))
            .build();

    /**
     * Registers an add operation handler or a remove operation handler if one was provided to the constructor.
     */
    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
    }

    @Override
    public void registerChildren(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerSubModel(TrackerTypeDefinition.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerReadWriteAttribute(SHOW_COOL_DEPLOYMENTS, TrackerShowCoolReadHandler.INSTANCE, TrackerShowCoolWriteHandler.INSTANCE);
    }
}
