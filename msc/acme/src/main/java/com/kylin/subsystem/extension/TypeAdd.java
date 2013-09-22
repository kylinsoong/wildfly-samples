package com.kylin.subsystem.extension;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;

public class TypeAdd extends AbstractAddStepHandler {
	
	public static final TypeAdd INSTANCE = new TypeAdd();

    private TypeAdd() {
    }

	@Override
	protected void populateModel(ModelNode operation, ModelNode model)
			throws OperationFailedException {
		// TODO Auto-generated method stub

	}

}
