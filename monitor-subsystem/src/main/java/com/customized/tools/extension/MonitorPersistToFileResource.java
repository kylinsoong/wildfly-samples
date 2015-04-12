package com.customized.tools.extension;

import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.ReloadRequiredWriteAttributeHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import static com.customized.tools.extension.CommonAttributes.PERSIST_MODEL;
import static com.customized.tools.extension.CommonAttributes.PERSISTTOFILE;
import static com.customized.tools.extension.CommonAttributes.ATTR_ISPERSIST;
import static com.customized.tools.extension.CommonAttributes.BOOLEAN_FALSE;

public class MonitorPersistToFileResource extends SimpleResourceDefinition {
	
	static final PathElement PATH_ELEMENT = PathElement.pathElement(PERSIST_MODEL, PERSISTTOFILE);
	
	static final SimpleAttributeDefinition IS_PERSIST = SimpleAttributeDefinitionBuilder.create(ATTR_ISPERSIST, ModelType.BOOLEAN, true)
			   								.setDefaultValue(new ModelNode(BOOLEAN_FALSE))
			   								.setAllowExpression(true)
			   								.build();
	
	static MonitorPersistToFileResource INSTANCE = new MonitorPersistToFileResource();

	MonitorPersistToFileResource() {
		super(PATH_ELEMENT, 
			  MonitorExtension.getResourceDescriptionResolver(PERSIST_MODEL), 
			  MonitorPersistToFileResourceAdd.INSTANCE, 
			  MonitorPersistToFileResourceRemove.INSTANCE);
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		final OperationStepHandler writeHandler = new ReloadRequiredWriteAttributeHandler(IS_PERSIST);
		resourceRegistration.registerReadWriteAttribute(IS_PERSIST, null, writeHandler);
	}
	
	
}
