package org.jboss.staxmapper.quickstart;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * What's this?
 *   Illustrate use The Cursor API write XML file, run StaxXMLStreamWriter will generate a XML file employee.xml
 * 
 * @author kylin
 *
 */
public class StaxXMLStreamWriter {

	public static void main(String[] args) throws XMLStreamException, Exception {
		
		XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out, "UTF-8");
		
		xmlStreamWriter.writeStartElement("subsystem");
		
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeStartElement("archive-validation");
		xmlStreamWriter.writeAttribute("enabled", "true");
		xmlStreamWriter.writeAttribute("fail-on-error", "true");
		xmlStreamWriter.writeAttribute("fail-on-warn", "false");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeStartElement("bean-validation");
		xmlStreamWriter.writeAttribute("enabled", "true");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeStartElement("default-workmanager");
		
		xmlStreamWriter.writeCharacters("\n\t\t");
		xmlStreamWriter.writeStartElement("short-running-threads");
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("core-threads");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("queue-length");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("max-threads");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("keepalive-time");
		xmlStreamWriter.writeAttribute("time", "10");
		xmlStreamWriter.writeAttribute("unit", "seconds");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t");
		xmlStreamWriter.writeStartElement("long-running-threads");
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("core-threads");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("queue-length");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("max-threads");
		xmlStreamWriter.writeAttribute("count", "50");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t\t");
		xmlStreamWriter.writeStartElement("keepalive-time");
		xmlStreamWriter.writeAttribute("time", "10");
		xmlStreamWriter.writeAttribute("unit", "seconds");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t\t");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeStartElement("cached-connection-manager");
		xmlStreamWriter.writeCharacters("\n\t");
		xmlStreamWriter.writeEndElement();
		
		xmlStreamWriter.writeCharacters("\n");
        xmlStreamWriter.writeEndElement();
        
        xmlStreamWriter.flush();
        xmlStreamWriter.close();
	}

}
