package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.junit.Assert.assertEquals;

import static com.customized.tools.extension.CommonAttributes.PATH_MODEL;
import static com.customized.tools.extension.CommonAttributes.PERSIST_MODEL;
import static com.customized.tools.extension.CommonAttributes.FOLDERPATH;
import static com.customized.tools.extension.CommonAttributes.RESULTFILENAME;
import static com.customized.tools.extension.CommonAttributes.PERSISTTOFILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.subsystem.test.AbstractSubsystemTest;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

public class MonitorParsingTestCase extends AbstractSubsystemTest {

	public MonitorParsingTestCase() {
		super(MonitorExtension.SUBSYSTEM_NAME, new MonitorExtension());
	}
	
	@Test
    public void testParseSubsystem() throws Exception {
		
		String subsystemXml = loadSubsystem();
		
		System.out.println(subsystemXml);
		
		List<ModelNode> operations = super.parse(subsystemXml);
		
		System.out.println(operations);
		
		assertEquals(4, operations.size());
		
		ModelNode addSubsystem = operations.get(0);
		assertEquals(ADD, addSubsystem.get(OP).asString());
		
		PathAddress addr = PathAddress.pathAddress(addSubsystem.get(OP_ADDR));
		assertEquals(1, addr.size());
		PathElement element = addr.getElement(0);
		assertEquals(SUBSYSTEM, element.getKey());
		assertEquals(MonitorExtension.SUBSYSTEM_NAME, element.getValue());

	}
	
	@Test
	public void testWriteSubsystem() throws IOException, XMLStreamException {
		
		ModelNode node = new ModelNode();
		
		node.get("path-model").get("folder").get("folderName").set("${jboss.server.base.dir}");
		node.get("path-model").get("file").get("fileName").set("monitor.out");
		node.get("persist-model").get("persist").get("isPersist").set(true);
		
		System.out.println(node);
		
		if(node.hasDefined(PATH_MODEL)){
        	ModelNode pathModel = node.get(PATH_MODEL);
        	if(pathModel.hasDefined(FOLDERPATH)){
        		System.out.println(pathModel.get(FOLDERPATH));
        	}
        	if(pathModel.hasDefined(RESULTFILENAME)){
        		System.out.println(pathModel.get(RESULTFILENAME));
        	}
        }
        
        if(node.hasDefined(PERSIST_MODEL)) {
        	ModelNode valueModel = node.get(PERSIST_MODEL);
        	if(valueModel.hasDefined(PERSISTTOFILE)){
        		System.out.println(valueModel.get(PERSISTTOFILE));
        	}
        }
		
	}
	
	private String loadSubsystem() throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("monitor.xml")));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		
		return fileData.toString();
	}
   
}
