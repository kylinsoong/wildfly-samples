package com.acme.corp.tracker.extension;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.logging.Logger;

/**
 * Handler responsible for removing the subsystem resource from the model
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class SubsystemRemoveHandler extends AbstractRemoveStepHandler {

    static final SubsystemRemoveHandler INSTANCE = new SubsystemRemoveHandler();

    private final Logger log = Logger.getLogger(SubsystemRemoveHandler.class);

    private SubsystemRemoveHandler() {
    }
}
