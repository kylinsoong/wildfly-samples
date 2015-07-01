package org.wildfly.examples.configuration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.as.logging.LoggingSubsystemParserWrapper;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLMapper;

public class LoggingConfigurationParser {

	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError, IOException {
		
		QName rootElement = new QName("urn:jboss:domain:logging:3.0", "subsystem");
		LoggingSubsystemParserWrapper rootParser = new LoggingSubsystemParserWrapper();
		final XMLMapper mapper = XMLMapper.Factory.create();
		mapper.registerRootElement(rootElement, rootParser);
		
		final List<ModelNode> updates = new ArrayList<ModelNode>();
		final FileInputStream fis = new FileInputStream("subsystem-logging.xml");
		BufferedInputStream input = new BufferedInputStream(fis);
		XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(input);
		mapper.parseDocument(updates, streamReader);
		streamReader.close();
		input.close();
        fis.close();
        
        for(ModelNode model : updates) {
        	System.out.println(model.toJSONString(true));
        }
        

	}
	
	

}
