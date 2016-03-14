package com.acme.corp.tracker.extension;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

public class TrackerSubsystemDefinition extends SimpleResourceDefinition {

    protected TrackerSubsystemDefinition() {
        super(TrackerExtension.SUBSYSTEM_PATH, TrackerExtension.getResourceDescriptionResolver(null), SubsystemAddHandler.INSTANCE, SubsystemRemoveHandler.INSTANCE);
    }
    
    protected TrackerSubsystemDefinition(Parameters parameters){
        super(parameters);
    }

    /**
     * {@inheritDoc}
     * Registers an add operation handler or a remove operation handler if one was provided to the constructor.
     */
    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        //We always need to add a 'describe' operation
//        resourceRegistration.registerOperationHandler(DESCRIBE, GenericSubsystemDescribeHandler.INSTANCE, GenericSubsystemDescribeHandler.INSTANCE, false, OperationEntry.EntryType.PRIVATE);
        resourceRegistration.registerSubModel(TypeDefinition.INSTANCE);
    }
}
