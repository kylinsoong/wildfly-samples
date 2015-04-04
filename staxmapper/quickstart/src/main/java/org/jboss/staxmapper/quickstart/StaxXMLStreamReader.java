package org.jboss.staxmapper.quickstart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * What's this?
 *   Illustrate use The Cursor API read XML file, run StaxXMLStreamReader will read xml content and output to console
 * 
 * @author kylin
 *
 */
public class StaxXMLStreamReader {

	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("subsystem-jca.xml"));
		
		
		while (true) {
			
			if(reader.getEventType() == XMLStreamConstants.END_DOCUMENT) {
				break;
			}
			
			if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
				
				String cursor = reader.getLocalName();
				
				if(cursor.equals("core-threads")) {
					String value = reader.getAttributeValue(0);
					System.out.println(value);
				} else if(cursor.equals("queue-length")) {
					String value = reader.getAttributeValue(0);
					System.out.println(value);
				} else if(cursor.equals("max-threads")) {
					String value = reader.getAttributeValue(0);
					System.out.println(value);
				} else if(cursor.equals("keepalive-time")){
					System.out.println(reader.getAttributeValue(null, "time") + " - " + reader.getAttributeValue(null, "unit"));
				}
			}
			
			reader.next();
		}
		
	}

}
