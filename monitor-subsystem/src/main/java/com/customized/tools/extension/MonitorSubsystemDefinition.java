package com.customized.tools.extension;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

/**
 * @author <a href="mailto:tcerar@redhat.com">Tomaz Cerar</a>
 */
public class MonitorSubsystemDefinition extends PersistentResourceDefinition {

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
        super.registerOperations(resourceRegistration);
        //you can register additional operations here
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Arrays.asList(ATTRIBUTES);
    }
}
