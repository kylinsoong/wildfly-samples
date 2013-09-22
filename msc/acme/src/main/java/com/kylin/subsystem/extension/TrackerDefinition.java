package com.kylin.subsystem.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIBE;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.OperationEntry;

public class TrackerDefinition extends SimpleResourceDefinition {
	
	public static final TrackerDefinition INSTANCE = new TrackerDefinition() ;
	
	private TrackerDefinition() {
		super(TrackerExtension.SUBSYSTEM_PATH, TrackerExtension.getResourceDescriptionResolver(null), SubsystemAdd.INSTANCE, SubsystemRemove.INSTANCE);
	}

	public void registerOperations(ManagementResourceRegistration resourceRegistration) {
		super.registerOperations(resourceRegistration);
		 resourceRegistration.registerOperationHandler(DESCRIBE, GenericSubsystemDescribeHandler.INSTANCE, GenericSubsystemDescribeHandler.INSTANCE, false, OperationEntry.EntryType.PRIVATE);
	}

}
