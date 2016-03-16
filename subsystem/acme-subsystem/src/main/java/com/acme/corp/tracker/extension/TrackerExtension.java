package com.acme.corp.tracker.extension;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.SimpleResourceDefinition.Parameters;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.registry.OperationEntry;

import com.acme.corp.tracker.handler.SubsystemAddHandler;
import com.acme.corp.tracker.handler.SubsystemRemoveHandler;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

public class TrackerExtension implements Extension {

    /**
     * The name space used for the {@code subsystem} element
     */
    public static final String NAMESPACE_1_0 = "urn:com.acme.corp.tracker:1.0";

    /**
     * The name of our subsystem within the model.
     */
    public static final String SUBSYSTEM_NAME = "tracker";

    /**
     * The parser used for parsing our subsystem
     */
    private final TrackerSubsystemParser parser = new TrackerSubsystemParser();

    private static final String RESOURCE_NAME = TrackerExtension.class.getPackage().getName() + ".LocalDescriptions";

    protected static final String TYPE = "type";
    
    protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
    
    protected static final PathElement TYPE_PATH = PathElement.pathElement(TYPE);
    
    private static final int MANAGEMENT_API_MAJOR_VERSION = 1;
    private static final int MANAGEMENT_API_MINOR_VERSION = 0;
    private static final int MANAGEMENT_API_MICRO_VERSION = 0;

    private static final ModelVersion CURRENT_VERSION = ModelVersion.create(MANAGEMENT_API_MAJOR_VERSION, MANAGEMENT_API_MINOR_VERSION, MANAGEMENT_API_MICRO_VERSION);

    static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix) {
        String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
        return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME, TrackerExtension.class.getClassLoader(), true, false);
    }

    @Override
    public void initializeParsers(ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE_1_0, parser);
    }

    @Override
    public void initialize(ExtensionContext context) {
        
        Parameters parameters = new Parameters(SUBSYSTEM_PATH, getResourceDescriptionResolver(null));
        parameters.setAddHandler(SubsystemAddHandler.INSTANCE)
                  .setRemoveHandler(SubsystemRemoveHandler.INSTANCE)
                  .setAddRestartLevel(OperationEntry.Flag.RESTART_NONE)
                  .setRemoveRestartLevel(OperationEntry.Flag.RESTART_RESOURCE_SERVICES)
                  .setDeprecationData(null)
                  .setCapabilities();    
        TrackerSubsystemDefinition resourceDefinition = new TrackerSubsystemDefinition(parameters);
        
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, CURRENT_VERSION);
        subsystem.registerSubsystemModel(resourceDefinition);
        subsystem.registerXMLElementWriter(parser);
    }
    
    //TODO--
    public static void main(String[] args) {
        
        System.out.println(TYPE_PATH);
    }

}
