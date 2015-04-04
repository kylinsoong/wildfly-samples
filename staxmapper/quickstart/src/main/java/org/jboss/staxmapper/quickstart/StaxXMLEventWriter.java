package org.jboss.staxmapper.quickstart;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * What's this?
 *   Illustrate use The Event Iterator API write XML file, run StaxXMLEventWriter will generate a XML file item.xml
 * 
 * @author kylin
 *
 */
public class StaxXMLEventWriter {

	public static void main(String[] args) throws XMLStreamException {

		XMLOutputFactory factory = XMLOutputFactory.newInstance();
//		factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
		XMLEventWriter eventWriter = factory.createXMLEventWriter(System.out);
		
		// Create a EventFactory
	    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent end = eventFactory.createDTD("\n");
	    XMLEvent tab = eventFactory.createDTD("\t");
	    XMLEvent tab2 = eventFactory.createDTD("\t\t");
	    XMLEvent tab3 = eventFactory.createDTD("\t\t\t");
	    QName subsystem = new QName("urn:jboss:domain:jca:2.0", "subsystem");
	    
		// Create and write Start Tag
	    StartDocument startDocument = eventFactory.createStartDocument();
	    eventWriter.add(startDocument);
	    eventWriter.add(end);
	    
		// Create config open tag
	    StartElement configStartElement = eventFactory.createStartElement(subsystem, null, null);
	    eventWriter.add(configStartElement);
	    eventWriter.add(end);

	    eventWriter.add(tab);
		eventWriter.add(eventFactory.createStartElement("", "", "archive-validation"));
		eventWriter.add(eventFactory.createAttribute("enabled", "true"));
		eventWriter.add(eventFactory.createAttribute("fail-on-error", "true"));
		eventWriter.add(eventFactory.createAttribute("fail-on-warn", "false"));
	    eventWriter.add(eventFactory.createEndElement("", "", "archive-validation"));
		eventWriter.add(end);
		
		eventWriter.add(tab);
		eventWriter.add(eventFactory.createStartElement("", "", "bean-validation"));
		eventWriter.add(eventFactory.createAttribute("enabled", "true"));
	    eventWriter.add(eventFactory.createEndElement("", "", "bean-validation"));
		eventWriter.add(end);
		
		eventWriter.add(tab);
		eventWriter.add(eventFactory.createStartElement("", "", "default-workmanager"));
		eventWriter.add(end);
		
		eventWriter.add(tab2);
		eventWriter.add(eventFactory.createStartElement("", "", "short-running-threads"));
		eventWriter.add(end);
		
		eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "core-threads"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "core-threads"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "queue-length"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "queue-length"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "max-threads"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "max-threads"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "keepalive-time"));
		eventWriter.add(eventFactory.createAttribute("time", "10"));
		eventWriter.add(eventFactory.createAttribute("unit", "seconds"));
	    eventWriter.add(eventFactory.createEndElement("", "", "keepalive-time"));
	    eventWriter.add(end);
		
	    eventWriter.add(tab2);
	    eventWriter.add(eventFactory.createEndElement("", "", "short-running-threads"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab2);
		eventWriter.add(eventFactory.createStartElement("", "", "long-running-threads"));
		eventWriter.add(end);
		
		eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "core-threads"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "core-threads"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "queue-length"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "queue-length"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "max-threads"));
		eventWriter.add(eventFactory.createAttribute("count", "50"));
	    eventWriter.add(eventFactory.createEndElement("", "", "max-threads"));
	    eventWriter.add(end);
	    
	    eventWriter.add(tab3);
		eventWriter.add(eventFactory.createStartElement("", "", "keepalive-time"));
		eventWriter.add(eventFactory.createAttribute("time", "10"));
		eventWriter.add(eventFactory.createAttribute("unit", "seconds"));
	    eventWriter.add(eventFactory.createEndElement("", "", "keepalive-time"));
	    eventWriter.add(end);
		
	    eventWriter.add(tab2);
	    eventWriter.add(eventFactory.createEndElement("", "", "long-running-threads"));
	    eventWriter.add(end);
		
		eventWriter.add(tab);
	    eventWriter.add(eventFactory.createEndElement("", "", "default-workmanager"));
		eventWriter.add(end);
		
		eventWriter.add(tab);
		eventWriter.add(eventFactory.createStartElement("", "", "cached-connection-manager"));
	    eventWriter.add(eventFactory.createEndElement("", "", "cached-connection-manager"));
		eventWriter.add(end);
	    
	    eventWriter.add(eventFactory.createEndElement("", "", "subsystem"));
	    eventWriter.add(end);
	    eventWriter.add(eventFactory.createEndDocument());
	    eventWriter.close();
	}

}
