package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;

public class MonitorPersistToFileResource extends MonitorModelResource{
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.PERSIST_MODEL, CommonAttributes.PERSISTTOFILE);

	MonitorPersistToFileResource() {
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.PERSIST_MODEL + "." + PATH_ELEMENT.getValue()), MonitorSubsystemDefinition.IS_PERSIST);
	}
}
