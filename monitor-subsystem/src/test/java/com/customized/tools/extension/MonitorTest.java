package com.customized.tools.extension;

import org.jboss.as.controller.PathElement;
import org.junit.Test;

public class MonitorTest {

	@Test
	public void testPathElement() {
		System.out.println(MonitorExtension.class.getPackage().getName() + ".LocalDescriptions");
		System.out.println(MonitorPersistToFileResource.PATH_ELEMENT.getValue());
		System.out.println(CommonAttributes.PERSIST_MODEL + "." + MonitorPersistToFileResource.PATH_ELEMENT.getValue());
		
		System.out.println(PathElement.pathElement(CommonAttributes.PATH_MODEL, CommonAttributes.RESULTFILENAME).getValue());
		System.out.println(CommonAttributes.PATH_MODEL + "." + MonitorFileNameModelResource.PATH_ELEMENT.getValue());
		
		System.out.println(MonitorFolderPathModelResource.PATH_ELEMENT.getValue());
		System.out.println(CommonAttributes.PATH_MODEL + "." + MonitorFolderPathModelResource.PATH_ELEMENT.getValue());

	}
}
