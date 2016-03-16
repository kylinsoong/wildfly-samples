package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleOperationDefinition;
import org.jboss.as.controller.SimpleOperationDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.acme.corp.tracker.handler.SubsystemAddHandler;
import com.acme.corp.tracker.handler.SubsystemRemoveHandler;
import com.acme.corp.tracker.handler.TrackerDisableAllHandler;
import com.acme.corp.tracker.handler.TrackerListAllHandler;
import com.acme.corp.tracker.handler.TrackerListCoolHandler;
import com.acme.corp.tracker.handler.TrackerShowCoolReadHandler;
import com.acme.corp.tracker.handler.TrackerShowCoolWriteHandler;

public class TrackerSubsystemDefinition extends SimpleResourceDefinition {
    
    public static String SHOW_COOL = "show-cool-deployments";
    public static String DISABLE_ALL_TRACKER = "disable-all-tracker";
    public static String LIST_DEPLOYMENTS = "list-deployments";
    public static String LIST_COOL_DEPLOYMENTS = "list-cool-deployments";

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
    
    public static final SimpleOperationDefinition DISABLE_ALL_TRACKER_OPER = new SimpleOperationDefinitionBuilder(DISABLE_ALL_TRACKER, TrackerExtension.getResourceDescriptionResolver(null))
            .setReplyType(ModelType.BOOLEAN)
            .setRuntimeOnly()
            .build();
    
    public static final SimpleOperationDefinition LIST_DEPLOYMENTS_OPER = new SimpleOperationDefinitionBuilder(LIST_DEPLOYMENTS, TrackerExtension.getResourceDescriptionResolver(null))
            .setReadOnly()
            .setRuntimeOnly()
            .setReplyType(ModelType.LIST)
            .setReplyValueType(ModelType.STRING)
            .build();
    
    public static final SimpleOperationDefinition LIST_COOL_DEPLOYMENTS_OPER = new SimpleOperationDefinitionBuilder(LIST_COOL_DEPLOYMENTS, TrackerExtension.getResourceDescriptionResolver(null))
            .setReadOnly()
            .setRuntimeOnly()
            .setReplyType(ModelType.LIST)
            .setReplyValueType(ModelType.STRING)
            .build();
    

    /**
     * Registers an add operation handler or a remove operation handler if one was provided to the constructor.
     */
    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
        resourceRegistration.registerOperationHandler(DISABLE_ALL_TRACKER_OPER, TrackerDisableAllHandler.INSTANCE);
        resourceRegistration.registerOperationHandler(LIST_DEPLOYMENTS_OPER, TrackerListAllHandler.INSTANCE);
        resourceRegistration.registerOperationHandler(LIST_COOL_DEPLOYMENTS_OPER, TrackerListCoolHandler.INSTANCE);
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
