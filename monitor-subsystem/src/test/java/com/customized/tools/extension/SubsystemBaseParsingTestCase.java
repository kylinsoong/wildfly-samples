package com.customized.tools.extension;

import java.io.IOException;

import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;
import org.junit.Ignore;

@Ignore
public class SubsystemBaseParsingTestCase extends AbstractSubsystemBaseTest {

	public SubsystemBaseParsingTestCase() {
		super(MonitorExtension.SUBSYSTEM_NAME, new MonitorExtension());
	}

	@Override
	protected String getSubsystemXml() throws IOException {
		return readResource("monitor.xml");
	}

   

}
