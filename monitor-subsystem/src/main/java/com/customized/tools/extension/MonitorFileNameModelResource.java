package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;

public class MonitorFileNameModelResource extends MonitorModelResource{

	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.RESULTFILE_MODEL, CommonAttributes.RESULTFILENAME);

	MonitorFileNameModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.RESULTFILE_MODEL + "." + PATH_ELEMENT.getValue()), MonitorSubsystemDefinition.PATH_NAME);
	}
	

}
