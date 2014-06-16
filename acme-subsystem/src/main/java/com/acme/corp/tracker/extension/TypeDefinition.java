package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;

public class TypeDefinition extends SimpleResourceDefinition {
	
	public static final TypeDefinition INSTANCE = new TypeDefinition();
	
	protected static final SimpleAttributeDefinition TICK =
			new SimpleAttributeDefinitionBuilder(TrackerExtension.TICK, ModelType.LONG)
			  .setAllowExpression(true)
			  .setXmlName(TrackerExtension.TICK)
			  .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
			  .setDefaultValue(new ModelNode(1000))
			  .setAllowNull(false)
			  .build();
	
	private TypeDefinition(){
		super(TYPE_PATH, TrackerExtension.getResourceDescriptionResolver(TYPE),TypeAdd.INSTANCE, TypeRemove.INSTANCE);
	}
	
	public void registerAttributes(ManagementResourceRegistration resourceRegistration){
		resourceRegistration.registerReadWriteAttribute(TICK, null, TrackerTickHandler.INSTANCE);
	}

}
