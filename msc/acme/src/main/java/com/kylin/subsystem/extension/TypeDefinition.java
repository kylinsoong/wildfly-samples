package com.kylin.subsystem.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

public class TypeDefinition extends SimpleResourceDefinition{
	
	public static final TypeDefinition INSTANCE = new TypeDefinition();
	
	protected static final SimpleAttributeDefinition TICK = 
			new SimpleAttributeDefinitionBuilder(TrackerExtension.TICK, ModelType.LONG)
					.setAllowExpression(true)
					.setXmlName(TrackerExtension.TICK)
					.setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
					.setDefaultValue(new ModelNode(1000))
					.setAllowNull(true)
					.build();
	
	private TypeDefinition() {
		super(TrackerExtension.TYPE_PATH, TrackerExtension.getResourceDescriptionResolver(TrackerExtension.TYPE), TypeAdd.INSTANCE, TypeRemove.INSTANCE);
	}

	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		resourceRegistration.registerReadWriteAttribute(TICK, null, TrackerTickHandler.INSTANCE);
	}

}
