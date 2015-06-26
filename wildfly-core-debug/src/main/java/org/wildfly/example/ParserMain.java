package org.wildfly.example;

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

import org.jboss.as.controller.parsing.Namespace;
import org.jboss.as.server.parsing.StandaloneXml;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLMapper;


public class ParserMain {
	
	private static final String XML = "/home/kylin/src/wildfly-core/build/target/wildfly-core-2.0.0.Alpha5-SNAPSHOT/standalone/configuration/standalone.xml";

	public static void main(String[] args) throws XMLStreamException, FactoryConfigurationError, IOException {
		
		QName rootElement = new QName(Namespace.CURRENT.getUriString(), "server");
		StandaloneXml rootParser = new StandaloneXml(null, null, null);
		
		final XMLMapper mapper = XMLMapper.Factory.create();
		mapper.registerRootElement(rootElement, rootParser);
		
		final List<ModelNode> updates = new ArrayList<>();
		final FileInputStream fis = new FileInputStream(XML);
		
		BufferedInputStream input = new BufferedInputStream(fis);
		XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(input);
		mapper.parseDocument(updates, streamReader);
		streamReader.close();
		input.close();
        fis.close();

	}
	
	
	

}
