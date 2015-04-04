package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import java.io.File;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
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
    	
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, 1, 0);
        
        final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(MonitorSubsystemDefinition.INSTANCE);
        
        registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);

        subsystem.registerXMLElementWriter(parser);
    }

    @Override
    public void initializeParsers(ExtensionParsingContext context) {
    	log.info("MonitorExtension initializeParsers");
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, parser);
    }

    private static class MonitorSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {

        private final PersistentResourceXMLDescription xmlDescription;

        private MonitorSubsystemParser() {
            this.xmlDescription = PersistentResourceXMLDescription.builder(MonitorSubsystemDefinition.INSTANCE).build();
        }


        @Override
        public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
            ModelNode model = new ModelNode();
            model.get(MonitorSubsystemDefinition.INSTANCE.getPathElement().getKeyValuePair()).set(context.getModelNode());
            xmlDescription.persist(writer, model, MonitorExtension.NAMESPACE);
            
            ModelNode node = context.getModelNode();
            
            context.startSubsystemElement(MonitorExtension.NAMESPACE, false);
            
            writer.writeStartElement("folderPath");
            String value = node.get("folderPath").asString();
            writer.writeEndElement();
            
            writer.writeStartElement("resultFile");
            value = node.get("resultFile").asString();
            writer.writeEndElement();
            
            writer.writeStartElement("persist-to-file");
            value = node.get("persist-to-file").asString();
            writer.writeEndElement();
            
            writer.writeEndElement();
        }

        @Override
        public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        	
        	ParseUtils.requireNoAttributes(reader);
        	
        	final ModelNode subsystem = new ModelNode();
        	subsystem.get(OP).set(ADD);
        	subsystem.get(OP_ADDR).set(PathAddress.pathAddress(SUBSYSTEM_PATH).toModelNode());
        	list.add(subsystem);
        	
        	 while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
        		 if(reader.getLocalName().equals("folderPath")){
        			 String value = reader.getElementText();
//        			 if(!new File(value).exists()){
//        				 value = System.getProperty(value);
//        			 }
        			 subsystem.get("folderPath").set(value);
        		 } else if(reader.getLocalName().equals("resultFile")){
        			 String value = reader.getElementText();
        			 subsystem.get("resultFile").set(value);
        		 } else if(reader.getLocalName().equals("persist-to-file")){
        			 String value = reader.getElementText();
        			 subsystem.get("persist-to-file").set(value);
        		 }
        	 }
        	
        	 list.add(subsystem);
        }
    }

}
