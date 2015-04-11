package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;

public class MonitorFileNameModelResource extends MonitorModelResource{

	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.RESULTFILE_MODEL, CommonAttributes.RESULTFILENAME);
	
	static final SimpleAttributeDefinition FILE_NAME = SimpleAttributeDefinitionBuilder.create(CommonAttributes.FILENAME, ModelType.STRING, true)
			   .setAllowExpression(true)
			   .build();

	MonitorFileNameModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.RESULTFILE_MODEL + "." + PATH_ELEMENT.getValue()), FILE_NAME);
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		super.registerAttributes(resourceRegistration);
	}
	

}
