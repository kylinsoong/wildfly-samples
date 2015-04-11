package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;

public class MonitorFolderPathModelResource extends MonitorModelResource {
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.FOLDERPATH_MODEL, CommonAttributes.FOLDERPATH);
	
	static final SimpleAttributeDefinition FOLDER_NAME = SimpleAttributeDefinitionBuilder.create(CommonAttributes.FOLDERNAME, ModelType.STRING, true)
			   .setAllowExpression(true)
			   .build();

	MonitorFolderPathModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.FOLDERPATH_MODEL + "." + PATH_ELEMENT.getValue()), FOLDER_NAME);
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		super.registerAttributes(resourceRegistration);
	}

}
