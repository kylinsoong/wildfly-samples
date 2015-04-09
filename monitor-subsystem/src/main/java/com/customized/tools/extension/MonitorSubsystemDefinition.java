package com.customized.tools.extension;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.logging.Logger;


public class MonitorSubsystemDefinition extends SimpleResourceDefinition {
	
	private final Logger log = Logger.getLogger(MonitorSubsystemDefinition.class);
	
	public static final PathElement PATH_ELEMENT = PathElement.pathElement(ModelDescriptionConstants.SUBSYSTEM, MonitorExtension.SUBSYSTEM_NAME);
	
	
	static final SimpleAttributeDefinition PATH_NAME = SimpleAttributeDefinitionBuilder.create(CommonAttributes.NAME, ModelType.STRING, true)
													   .setAllowExpression(true)
													   .build();
	
	static final SimpleAttributeDefinition IS_PERSIST = SimpleAttributeDefinitionBuilder.create(CommonAttributes.VALUE, ModelType.BOOLEAN, true)
													   .setDefaultValue(new ModelNode(CommonAttributes.BOOLEAN_FALSE))
													   .build();

    static final AttributeDefinition[] ATTRIBUTES = { PATH_NAME,  IS_PERSIST};

    static final MonitorSubsystemDefinition INSTANCE = new MonitorSubsystemDefinition();

    private MonitorSubsystemDefinition() {
        super(PATH_ELEMENT, 
        	  MonitorExtension.getResourceDescriptionResolver(MonitorExtension.SUBSYSTEM_NAME), 
        	  MonitorSubsystemAdd.INSTANCE, 
        	  MonitorSubsystemRemove.INSTANCE);
        log.info("MonitorSubsystemDefinition constructor");
    }

	@Override
	public void registerChildren(ManagementResourceRegistration resourceRegistration) {
		resourceRegistration.registerSubModel(new MonitorFileNameModelResource());
		resourceRegistration.registerSubModel(new MonitorFolderPathModelResource());
		resourceRegistration.registerSubModel(new MonitorPersistToFileResource());
	}


}
