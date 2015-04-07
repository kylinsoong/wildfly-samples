package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.parsing.ParseUtils;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

public class MonitorExtension implements Extension {
	
	 private final Logger log = Logger.getLogger(MonitorExtension.class);

    /**
     * The name space used for the {@code substystem} element
     */
    public static final String NAMESPACE = "urn:com.customized.tools.monitor:1.0";
    
    /**
     * The name of our subsystem within the model.
     */
    public static final String SUBSYSTEM_NAME = "monitor";
    
    private static final int MANAGEMENT_API_MAJOR_VERSION = 3;
    private static final int MANAGEMENT_API_MINOR_VERSION = 0;
    private static final int MANAGEMENT_API_MICRO_VERSION = 0;

    private static final ModelVersion CURRENT_VERSION = ModelVersion.create(MANAGEMENT_API_MAJOR_VERSION, MANAGEMENT_API_MINOR_VERSION, MANAGEMENT_API_MICRO_VERSION);


    /**
     * The parser used for parsing our subsystem
     */
    private final MonitorSubsystemParser parser = new MonitorSubsystemParser();

    protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
    
    private static final String RESOURCE_NAME = MonitorExtension.class.getPackage().getName() + ".LocalDescriptions";

    static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix) {
        String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
        return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME, MonitorExtension.class.getClassLoader(), true, false);
    }
    
    @Override
    public void initialize(ExtensionContext context) {
    	
    	log.info("MonitorExtension initialize");
    	
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, CURRENT_VERSION);
        
        final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(MonitorSubsystemDefinition.INSTANCE);
        
        registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);

        subsystem.registerXMLElementWriter(parser);
    }

    @Override
    public void initializeParsers(ExtensionParsingContext context) {
    	log.info("MonitorExtension initializeParsers");
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, parser);
    }
    
    private static ModelNode createOperation(String name, String... addressElements) {
        final ModelNode op = new ModelNode();
        op.get(OP).set(name);
        op.get(OP_ADDR).add(SUBSYSTEM, SUBSYSTEM_NAME);
        for (int i = 0; i < addressElements.length; i++) {
            op.get(OP_ADDR).add(addressElements[i], addressElements[++i]);
        }
        return op;
    }

    private static class MonitorSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {

        private final PersistentResourceXMLDescription xmlDescription;

        private MonitorSubsystemParser() {
            this.xmlDescription = PersistentResourceXMLDescription.builder(MonitorSubsystemDefinition.INSTANCE).build();
        }


        @Override
        public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
        	System.out.println("TODO--");
        }

        @Override
        public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        	
        	ParseUtils.requireNoAttributes(reader);
        	
        	ModelNode add = createOperation(ADD);
            list.add(add);
        	
            while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
           	 final Element element = Element.forName(reader.getLocalName());
                switch (element){
                	case FOLEDER_PATH:
                		list.add(parseMonitorElement(reader, Element.FOLEDER_PATH.getLocalName()));
                		break;
                	case RESULT_FILE_NAME:
                		list.add(parseMonitorElement(reader, Element.RESULT_FILE_NAME.getLocalName()));
                		break;
                	case PERSIST_TO_FILE:
                		list.add(parseMonitorElement(reader, Element.PERSIST_TO_FILE.getLocalName()));
                		break;
                	default: {
                       throw ParseUtils.unexpectedElement(reader);
                   }
                }
           }
        }


		private ModelNode parseMonitorElement(XMLExtendedStreamReader reader, String name) {
			
			ModelNode op = createOperation(ADD, name, "name");
			
			for (int i = 0; i < reader.getAttributeCount(); i++) {
				final String value = reader.getAttributeValue(i);
				switch(value) {
					case "name":
						break;
					case "value":
						break;
					default:
						break;
				}
			}
			
			return null;
		}
    }

}
