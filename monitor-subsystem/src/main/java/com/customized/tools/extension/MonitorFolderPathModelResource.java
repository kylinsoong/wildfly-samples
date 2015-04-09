package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;

public class MonitorFolderPathModelResource extends MonitorModelResource {
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.FOLDERPATH_MODEL, CommonAttributes.FOLDERPATH);

	MonitorFolderPathModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.FOLDERPATH_MODEL + "." + PATH_ELEMENT.getValue()), MonitorSubsystemDefinition.PATH_NAME);
	}

}
