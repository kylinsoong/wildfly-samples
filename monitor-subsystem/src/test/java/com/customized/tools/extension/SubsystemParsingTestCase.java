package com.customized.tools.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.subsystem.test.AbstractSubsystemTest;
import org.jboss.dmr.ModelNode;
import org.junit.Test;

public class SubsystemParsingTestCase extends AbstractSubsystemTest {

	public SubsystemParsingTestCase() {
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
