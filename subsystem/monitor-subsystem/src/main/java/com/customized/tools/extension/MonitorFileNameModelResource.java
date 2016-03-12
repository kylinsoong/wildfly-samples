package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import static com.customized.tools.extension.CommonAttributes.PATH_MODEL;
import static com.customized.tools.extension.CommonAttributes.RESULTFILENAME;
import static com.customized.tools.extension.CommonAttributes.ATTR_FILENAME;
import static com.customized.tools.extension.CommonAttributes.DEFAULT_FILENAME;


public class MonitorFileNameModelResource extends MonitorPathModelResource{

	static final PathElement PATH_ELEMENT = PathElement.pathElement(PATH_MODEL, RESULTFILENAME);
	
	static final SimpleAttributeDefinition FILE_NAME = SimpleAttributeDefinitionBuilder.create(ATTR_FILENAME, ModelType.STRING, true)
			   .setAllowExpression(true)
			   .setDefaultValue(new ModelNode(DEFAULT_FILENAME))
			   .build();
	
	static MonitorFileNameModelResource INSTANCE = new MonitorFileNameModelResource();

	MonitorFileNameModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(PATH_MODEL + "." + PATH_ELEMENT.getValue()), FILE_NAME);
	}
	
	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		super.registerAttributes(resourceRegistration);
	}

}
