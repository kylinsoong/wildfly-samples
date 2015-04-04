package com.customized.tools.extension;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.logging.Logger;


public class MonitorSubsystemDefinition extends PersistentResourceDefinition {
	
	private final Logger log = Logger.getLogger(MonitorSubsystemDefinition.class);

    static final AttributeDefinition[] ATTRIBUTES = { /* you can include attributes here */ };

    static final MonitorSubsystemDefinition INSTANCE = new MonitorSubsystemDefinition();

    private MonitorSubsystemDefinition() {
        super(MonitorExtension.SUBSYSTEM_PATH,
                MonitorExtension.getResourceDescriptionResolver(null),
                //We always need to add an 'add' operation
                SubsystemAdd.INSTANCE,
                //Every resource that is added, normally needs a remove operation
                SubsystemRemove.INSTANCE);
    }

    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
    	log.info("MonitorSubsystemDefinition registerOperations");
        super.registerOperations(resourceRegistration);
        
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
    	log.info("MonitorSubsystemDefinition getAttributes");
        return Arrays.asList(ATTRIBUTES);
    }
}
