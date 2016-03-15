package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import static com.acme.corp.tracker.extension.TrackerExtension.TYPE;
import static com.acme.corp.tracker.extension.TrackerExtension.TYPE_PATH;

public class TrackerTypeDefinition extends SimpleResourceDefinition {
    
    public static final TrackerTypeDefinition INSTANCE = new TrackerTypeDefinition();

    protected static final SimpleAttributeDefinition TICK =
            new SimpleAttributeDefinitionBuilder(TrackerExtension.TICK, ModelType.LONG)
                    .setAllowExpression(true)
                    .setXmlName(TrackerExtension.TICK)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .setDefaultValue(new ModelNode(1000))
                    .setAllowNull(false)
                    .build();


    private TrackerTypeDefinition() {
        super(TYPE_PATH, TrackerExtension.getResourceDescriptionResolver(TYPE), TypeAdd.INSTANCE, TypeRemove.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerReadWriteAttribute(TICK, null, TrackerTickHandler.INSTANCE);
    }
}
