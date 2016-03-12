package com.customized.tools.extension;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.ReloadRequiredWriteAttributeHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.descriptions.ResourceDescriptionResolver;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

public class MonitorPathModelResource extends SimpleResourceDefinition{
	
	private AttributeDefinition attribute;

	public MonitorPathModelResource(PathElement pathElement, ResourceDescriptionResolver descriptionResolver, SimpleAttributeDefinition attribute, SimpleAttributeDefinition...otherAttributes) {
		super(pathElement, 
			  descriptionResolver, 
			  new MonitorPathModelResourceAdd(attribute, otherAttributes), 
			  new MonitorPathModelResourceRemove());
		this.attribute = attribute;
	}

	@Override
	public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
		final OperationStepHandler writeHandler = new ReloadRequiredWriteAttributeHandler(attribute);
		resourceRegistration.registerReadWriteAttribute(attribute, null, writeHandler);
	}
	
	
	


}
