package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleOperationDefinition;
import org.jboss.as.controller.SimpleOperationDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.acme.corp.tracker.handler.TypeAddHandler;
import com.acme.corp.tracker.handler.TypeDisableHandler;
import com.acme.corp.tracker.handler.TypeEnableHandler;
import com.acme.corp.tracker.handler.TypeRemoveHandler;
import com.acme.corp.tracker.handler.TypeTickReadHandler;
import com.acme.corp.tracker.handler.TypeTickWriteHandler;

import static com.acme.corp.tracker.extension.TrackerExtension.TYPE;
import static com.acme.corp.tracker.extension.TrackerExtension.TYPE_PATH;

public class TrackerTypeDefinition extends SimpleResourceDefinition {
    
    public static final TrackerTypeDefinition INSTANCE = new TrackerTypeDefinition();
    
    public static final String TICK = "tick";
    public static String DISABLE_TRACKER = "disable-tracker";
    public static String ENABLE_TRACKER = "enable-tracker";

    public static final SimpleAttributeDefinition TICK_ATTR = new SimpleAttributeDefinitionBuilder(TICK, ModelType.LONG)
                    .setAllowExpression(true)
                    .setXmlName(TICK)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .setDefaultValue(new ModelNode(1000))
                    .setAllowNull(false)
                    .build();
    
    public static final SimpleOperationDefinition DISABLE_TRACKER_OPER = new SimpleOperationDefinitionBuilder(DISABLE_TRACKER, TrackerExtension.getResourceDescriptionResolver(TYPE))
                    .setReplyType(ModelType.BOOLEAN)
                    .setRuntimeOnly()
                    .build();
    
    public static final SimpleOperationDefinition ENABLE_TRACKER_OPER = new SimpleOperationDefinitionBuilder(ENABLE_TRACKER, TrackerExtension.getResourceDescriptionResolver(TYPE))
                    .setReplyType(ModelType.BOOLEAN)
                    .setRuntimeOnly()
                    .build();


    private TrackerTypeDefinition() {
        super(TYPE_PATH, TrackerExtension.getResourceDescriptionResolver(TYPE), TypeAddHandler.INSTANCE, TypeRemoveHandler.INSTANCE);
    }

    @Override
    public void registerOperations( ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        resourceRegistration.registerOperationHandler(DISABLE_TRACKER_OPER, TypeDisableHandler.INSTANCE);
        resourceRegistration.registerOperationHandler(ENABLE_TRACKER_OPER, TypeEnableHandler.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerReadWriteAttribute(TICK_ATTR, TypeTickReadHandler.INSTANCE, TypeTickWriteHandler.INSTANCE);
    }
}
