package org.jboss.staxmapper.quickstart;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLMapper;

public class StaxmapperXMLElementReader {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		
		XMLElementReader<List> reader = new XMLElementReader<List>(){

			@SuppressWarnings("unchecked")
			@Override
			public void readElement(XMLExtendedStreamReader reader, List list)throws XMLStreamException {

				while (reader.hasNext()) {
										
		            switch (reader.next()) {
		                case START_ELEMENT:
		                    if ("core-threads".equals(reader.getLocalName())) {
		                    	list.add(reader.getAttributeValue(0));
		                    } else if ("queue-length".equals(reader.getLocalName())) {
		                    	list.add(reader.getAttributeValue(0));
		                    } else if ("max-threads".equals(reader.getLocalName())) {
		                    	list.add(reader.getAttributeValue(0));
		                    } else if ("keepalive-time".equals(reader.getLocalName())) {
		                    	list.add(reader.getAttributeValue(0));
		                    	list.add(reader.getAttributeValue(1));
		                    }		                    
		                   

		            }
		        }
			}};
		
		
		final XMLMapper mapper = XMLMapper.Factory.create();
		mapper.registerRootElement(new QName("urn:jboss:domain:jca:2.0", "subsystem"), reader);
		List<String> list = new ArrayList<>();
		mapper.parseDocument(list, XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("subsystem-jca.xml")));
		System.out.println(list);
	}
	

}
