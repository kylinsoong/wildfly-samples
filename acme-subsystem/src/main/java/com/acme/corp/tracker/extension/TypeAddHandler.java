package com.acme.corp.tracker.extension;

import java.util.Locale;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.descriptions.DescriptionProvider;
import org.jboss.dmr.ModelNode;

public class TypeAddHandler extends AbstractAddStepHandler implements DescriptionProvider {

	public static final TypeAddHandler INSTANCE = new TypeAddHandler();

	private TypeAddHandler() {
	}

	public ModelNode getModelDescription(Locale locale) {
		return null;
	}

	protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {

	}

}
