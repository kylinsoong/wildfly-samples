package org.jboss.staxmapper.quickstart;

import java.io.FileInputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * What's this?
 *   Illustrate use The Event Iterator API read XML file, run StaxXMLEventReader will read xml content and output to console
 * 
 * 
 * @author kylin
 *
 */
public class StaxXMLEventReader {
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {
		
		XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream("subsystem-jca.xml"));
		
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()){
				StartElement element = event.asStartElement();
				
				Iterator<Attribute> attributes = element.getAttributes();
				
				while (attributes.hasNext()) {
					Attribute attribute = attributes.next();
					if (attribute.getName().toString().equals("count")) {
						System.out.println(attribute.getValue());
					} else if (attribute.getName().toString().equals("time")) {
						System.out.println(attribute.getValue());
					} else if (attribute.getName().toString().equals("unit")) {
						System.out.println(attribute.getValue());
					}

				}
			}
		}
		
	}

}
