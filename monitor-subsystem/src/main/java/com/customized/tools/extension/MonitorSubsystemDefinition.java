package com.customized.tools.extension;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;
import org.jboss.logging.Logger;


public class MonitorSubsystemDefinition extends PersistentResourceDefinition {
	
	private final Logger log = Logger.getLogger(MonitorSubsystemDefinition.class);
	
	static final SimpleAttributeDefinition MONITOR_RESULT_LIST = SimpleAttributeDefinitionBuilder.create("monitor-result", ModelType.LIST, true).build();

    static final AttributeDefinition[] ATTRIBUTES = { MONITOR_RESULT_LIST };

    static final MonitorSubsystemDefinition INSTANCE = new MonitorSubsystemDefinition();

    private MonitorSubsystemDefinition() {
        super(MonitorExtension.SUBSYSTEM_PATH, MonitorExtension.getResourceDescriptionResolver(null), MonitorSubsystemAdd.INSTANCE, SubsystemRemove.INSTANCE);
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
