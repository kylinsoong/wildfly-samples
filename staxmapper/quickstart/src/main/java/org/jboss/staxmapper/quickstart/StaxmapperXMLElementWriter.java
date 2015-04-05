package org.jboss.staxmapper.quickstart;

import java.io.StringWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamWriter;
import org.jboss.staxmapper.XMLMapper;

public class StaxmapperXMLElementWriter {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError {
		
		
		XMLElementWriter writer = new XMLElementWriter(){

			@Override
			public void writeContent(XMLExtendedStreamWriter streamWriter, Object value) throws XMLStreamException {

				streamWriter.writeStartElement("subsystem");
		        streamWriter.writeNamespace("", "urn:jboss:domain:jca:2.0");
		        
		        streamWriter.writeStartElement("archive-validation");
		        streamWriter.writeAttribute("enabled", "true");
		        streamWriter.writeAttribute("fail-on-error", "true");
		        streamWriter.writeAttribute("fail-on-warn", "false");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("bean-validation");
		        streamWriter.writeAttribute("enabled", "true");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("default-workmanager");
		        streamWriter.writeStartElement("short-running-threads");
		        
		        streamWriter.writeStartElement("core-threads");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("queue-length");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("max-threads");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("keepalive-time");
		        streamWriter.writeAttribute("time", "10");
		        streamWriter.writeAttribute("unit", "second");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeEndElement();
		        streamWriter.writeStartElement("long-running-threads");
		        
		        streamWriter.writeStartElement("core-threads");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("queue-length");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("max-threads");
		        streamWriter.writeAttribute("count", "50");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("keepalive-time");
		        streamWriter.writeAttribute("time", "10");
		        streamWriter.writeAttribute("unit", "second");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeEndElement();
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeStartElement("cached-connection-manager");
		        streamWriter.writeEndElement();
		        
		        streamWriter.writeEndElement();
		        streamWriter.writeEndDocument();
		        streamWriter.close();
			}};

		final StringWriter strWriter = new StringWriter(512);
        final XMLMapper mapper = XMLMapper.Factory.create();
        mapper.deparseDocument(writer, new Object(), XMLOutputFactory.newInstance().createXMLStreamWriter(strWriter));
        
        System.out.println(strWriter.toString());
	}

}
