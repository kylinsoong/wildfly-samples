package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

public class MonitorPersistToFileResource extends MonitorModelResource{
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.PERSIST_MODEL, CommonAttributes.PERSISTTOFILE);
	
	static final SimpleAttributeDefinition IS_PERSIST = SimpleAttributeDefinitionBuilder.create(CommonAttributes.ISPERSIST, ModelType.BOOLEAN, true)
			   .setDefaultValue(new ModelNode(CommonAttributes.BOOLEAN_FALSE))
			   .build();

	MonitorPersistToFileResource() {
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.PERSIST_MODEL + "." + PATH_ELEMENT.getValue()), IS_PERSIST);
	}
	
	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		super.registerAttributes(resourceRegistration);
	}
}
