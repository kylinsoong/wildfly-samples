package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.OperationContext.Stage;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.Resource;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;


public class MonitorFileNameModelResource extends MonitorModelResource{
	
	static MonitorFileNameModelResource INSTANCE = new MonitorFileNameModelResource();

	static final PathElement PATH_ELEMENT = PathElement.pathElement(CommonAttributes.PATH_MODEL, CommonAttributes.RESULTFILENAME);
	
	static final SimpleAttributeDefinition FILE_NAME = SimpleAttributeDefinitionBuilder.create(CommonAttributes.FILENAME, ModelType.STRING, true)
			   .setAllowExpression(true)
			   .setDefaultValue(new ModelNode(CommonAttributes.DEFAULT_FILENAME))
			   .build();

	MonitorFileNameModelResource(){
		super(PATH_ELEMENT, MonitorExtension.getResourceDescriptionResolver(CommonAttributes.PATH_MODEL + "." + PATH_ELEMENT.getValue()), FILE_NAME);
	}

	
	

}
