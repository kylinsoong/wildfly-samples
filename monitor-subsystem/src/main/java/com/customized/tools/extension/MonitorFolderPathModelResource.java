package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import static com.customized.tools.extension.CommonAttributes.PATH_MODEL;
import static com.customized.tools.extension.CommonAttributes.FOLDERPATH;
import static com.customized.tools.extension.CommonAttributes.ATTR_FOLDERNAME;
import static com.customized.tools.extension.CommonAttributes.DEFAULT_FOLDERNAME;


public class MonitorFolderPathModelResource extends MonitorPathModelResource {
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(PATH_MODEL, FOLDERPATH);
	
	static final SimpleAttributeDefinition FOLDER_NAME = SimpleAttributeDefinitionBuilder.create(ATTR_FOLDERNAME, ModelType.STRING, true)
			   .setAllowExpression(true)
			   .setDefaultValue(new ModelNode(DEFAULT_FOLDERNAME))
			   .build();

	MonitorFolderPathModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(PATH_MODEL + "." + PATH_ELEMENT.getValue()), FOLDER_NAME);
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		super.registerAttributes(resourceRegistration);
	}

}
