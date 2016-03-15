package com.acme.corp.tracker.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static com.acme.corp.tracker.extension.TrackerExtension.SUBSYSTEM_PATH;
import static com.acme.corp.tracker.extension.TrackerExtension.TYPE;

import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.parsing.ParseUtils;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

class TrackerSubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {

    @Override
    public void writeContent(final XMLExtendedStreamWriter writer, final SubsystemMarshallingContext context) throws XMLStreamException {
        
        //Write out the main subsystem element
        context.startSubsystemElement(TrackerExtension.NAMESPACE_1_0, false);
        writer.writeStartElement("deployment-types");
        ModelNode node = context.getModelNode();
        ModelNode type = node.get(TYPE);
        for (Property property : type.asPropertyList()) {

            //write each child element to xml
            writer.writeStartElement("deployment-type");
            writer.writeAttribute("suffix", property.getName());
            ModelNode entry = property.getValue();
            TypeDefinition.TICK.marshallAsAttribute(entry, true, writer);
            writer.writeEndElement();
        }
        //End deployment-types
        writer.writeEndElement();
        //End subsystem
        writer.writeEndElement();
    }

    @Override
    public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        
        // Require no attributes
        ParseUtils.requireNoAttributes(reader);
        
        //Add the main subsystem 'add' operation
        final ModelNode subsystem = new ModelNode();
        subsystem.get(OP).set(ADD);
        subsystem.get(OP_ADDR).set(PathAddress.pathAddress(SUBSYSTEM_PATH).toModelNode());
        list.add(subsystem);
        
      //Read the children
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (!reader.getLocalName().equals("deployment-types")) {
                throw ParseUtils.unexpectedElement(reader);
            }
            while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
                if (reader.isStartElement()) {
                    readDeploymentType(reader, list);
                }
            }
        } 
    }
    
    private void readDeploymentType(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        if (!reader.getLocalName().equals("deployment-type")) {
            throw ParseUtils.unexpectedElement(reader);
        }
        ModelNode addTypeOperation = new ModelNode();
        addTypeOperation.get(OP).set(ModelDescriptionConstants.ADD);

        String suffix = null;
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            String attr = reader.getAttributeLocalName(i);
            String value = reader.getAttributeValue(i);
            if (attr.equals("tick")) {
                TypeDefinition.TICK.parseAndSetParameter(value, addTypeOperation, reader);
            } else if (attr.equals("suffix")) {
                suffix = value;
            } else {
                throw ParseUtils.unexpectedAttribute(reader, i);
            }
        }
        ParseUtils.requireNoContent(reader);
        if (suffix == null) {
            throw ParseUtils.missingRequiredElement(reader, Collections.singleton("suffix"));
        }

        //Add the 'add' operation for each 'type' child
        PathAddress addr = PathAddress.pathAddress(SUBSYSTEM_PATH, PathElement.pathElement(TYPE, suffix));
        addTypeOperation.get(OP_ADDR).set(addr.toModelNode());
        list.add(addTypeOperation);
    }

}
