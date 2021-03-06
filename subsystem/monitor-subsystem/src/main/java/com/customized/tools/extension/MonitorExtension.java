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
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.parsing.ParseUtils;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

public class MonitorExtension implements Extension {
	
	 private final Logger log = Logger.getLogger(MonitorExtension.class);
    
    /**
     * The name of our subsystem within the model.
     */
    public static final String SUBSYSTEM_NAME = "monitor";
    private static final String RESOURCE_NAME = MonitorExtension.class.getPackage().getName() + ".LocalDescriptions";
    
    private static final int MANAGEMENT_API_MAJOR_VERSION = 3;
    private static final int MANAGEMENT_API_MINOR_VERSION = 0;
    private static final int MANAGEMENT_API_MICRO_VERSION = 0;

    private static final ModelVersion CURRENT_VERSION = ModelVersion.create(MANAGEMENT_API_MAJOR_VERSION, MANAGEMENT_API_MINOR_VERSION, MANAGEMENT_API_MICRO_VERSION);

    /**
     * The parser used for parsing our subsystem
     */
    private final MonitorSubsystemParser parser = new MonitorSubsystemParser();
    private final MonitorSubsystemWriter writer = new MonitorSubsystemWriter();


    static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix) {
        return new StandardResourceDescriptionResolver(keyPrefix, RESOURCE_NAME, MonitorExtension.class.getClassLoader(), true, false);
    }
    
    @Override
    public void initializeParsers(ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, CommonAttributes.NAMESPACE_MONITOR_1_0, parser);
        log.info(context.getProcessType() + " set monitor subsystem XML Mapping parser");
    }
    
    @Override
    public void initialize(ExtensionContext context) {
    	
    	log.info(context.getProcessType() + " RunningMode: " + context.getRunningMode() + " register Subsystem, SubsystemModel, XMLElementWriter");
    	
        final SubsystemRegistration registration = context.registerSubsystem(SUBSYSTEM_NAME, CURRENT_VERSION);
        
        registration.registerSubsystemModel(MonitorSubsystemRootResource.INSTANCE);
        registration.registerXMLElementWriter(writer);        
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

    private static class MonitorSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>{

        @Override
        public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        	
        	ParseUtils.requireNoAttributes(reader);
        	
        	ModelNode add = createOperation(ADD);
            list.add(add);
        	
            while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
           	 	String name = reader.getLocalName();
                switch (name){
                	case CommonAttributes.ELEMENT_FOLDER_PATH :
                		list.add(parseModelElement(reader, CommonAttributes.PATH_MODEL, CommonAttributes.FOLDERPATH));
                		break;
                	case CommonAttributes.ELEMENT_RESULT_FILE_NAME:
                		list.add(parseModelElement(reader, CommonAttributes.PATH_MODEL, CommonAttributes.RESULTFILENAME));
                		break;
                	case CommonAttributes.ELEMENT_PERSIST_TO_FILE:
                		list.add(parseModelElement(reader, CommonAttributes.PERSIST_MODEL, CommonAttributes.PERSISTTOFILE));
                		break;
                	default: {
                       throw ParseUtils.unexpectedElement(reader);
                   }
                }
           }
        }


		private ModelNode parseModelElement(XMLExtendedStreamReader reader, String model, String address) throws XMLStreamException {
			
			ModelNode op = createOperation(ADD, model, address);
			
            for (int i = 0; i < reader.getAttributeCount(); i++) {
            	final String value = reader.getAttributeValue(i);
            	final String attr = reader.getAttributeLocalName(i);
            	switch(attr){
            	case CommonAttributes.ATTR_FOLDERNAME:
            		MonitorFolderPathModelResource.FOLDER_NAME.parseAndSetParameter(value, op, reader);
            		break;
            	case CommonAttributes.ATTR_FILENAME:
            		MonitorFileNameModelResource.FILE_NAME.parseAndSetParameter(value, op, reader);
            		break;
            	case CommonAttributes.ATTR_ISPERSIST:
            		MonitorPersistToFileResource.IS_PERSIST.parseAndSetParameter(value, op, reader);
            		break;
            	default:
                    throw ParseUtils.unexpectedAttribute(reader, i);
            	}
            }
            
            ParseUtils.requireNoContent(reader);
            
			return op;
		}

    }
    
    
    private static class MonitorSubsystemWriter implements XMLStreamConstants, XMLElementWriter<SubsystemMarshallingContext> {

		public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
			
			final ModelNode node = context.getModelNode();

            context.startSubsystemElement(CommonAttributes.NAMESPACE_MONITOR_1_0, false);
            
            if(node.hasDefined(CommonAttributes.PATH_MODEL)){
            	ModelNode pathModel = node.get(CommonAttributes.PATH_MODEL);
            	if(pathModel.hasDefined(CommonAttributes.FOLDERPATH)){
            		writer.writeEmptyElement(CommonAttributes.ELEMENT_FOLDER_PATH);
            		MonitorFolderPathModelResource.FOLDER_NAME.marshallAsAttribute(pathModel.get(CommonAttributes.ATTR_FOLDERNAME), false, writer);
            	}
            	if(pathModel.hasDefined(CommonAttributes.RESULTFILENAME)){
            		writer.writeEmptyElement(CommonAttributes.ELEMENT_RESULT_FILE_NAME);
            		MonitorFileNameModelResource.FILE_NAME.marshallAsAttribute(pathModel.get(CommonAttributes.ATTR_FILENAME), false, writer);
            	}
            }
            
            if(node.hasDefined(CommonAttributes.PERSIST_MODEL)) {
            	ModelNode valueModel = node.get(CommonAttributes.PERSIST_MODEL);
            	if(valueModel.hasDefined(CommonAttributes.PERSISTTOFILE)){
            		writer.writeEmptyElement(CommonAttributes.ELEMENT_PERSIST_TO_FILE);
            		MonitorPersistToFileResource.IS_PERSIST.marshallAsAttribute(valueModel.get(CommonAttributes.ATTR_ISPERSIST), false, writer);
            	}
            }
            
            writer.writeEndElement();
		}
    	
    }

}
