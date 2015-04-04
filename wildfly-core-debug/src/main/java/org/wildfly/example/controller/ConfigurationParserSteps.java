package org.wildfly.example.controller;

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

import org.jboss.as.server.parsing.StandaloneXml;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLMapper;

public class ConfigurationParserSteps {
	
	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError, IOException  {
		
		QName rootElement = new QName("{urn:jboss:domain:2.2}server");
		
		XMLElementReader<List<ModelNode>> rootParser = new StandaloneXml(null, null, null); ;
		
		final XMLMapper mapper = XMLMapper.Factory.create();
		
		mapper.registerRootElement(rootElement, rootParser);
		
		final List<ModelNode> updates = new ArrayList<ModelNode>();
		
		final FileInputStream fis = new FileInputStream("configuration/standalone.xml");
		try {
			BufferedInputStream input = new BufferedInputStream(fis);
			XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(input);
			mapper.parseDocument(updates, streamReader);
            streamReader.close();
            input.close();
            fis.close();
		} finally {
			fis.close();
		}
		
		System.out.println(updates.size());
	}

}
