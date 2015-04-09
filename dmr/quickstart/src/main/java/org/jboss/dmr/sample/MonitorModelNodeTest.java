package org.jboss.dmr.sample;

import org.jboss.dmr.ModelNode;

public class MonitorModelNodeTest {

	public static void main(String[] args) {

		final ModelNode op = new ModelNode();
		op.get("operation").set("name");
		op.get("address").add("subsystem", "monitor");
		op.get("address").add("key", "value");
		
		System.out.println(op);
	}

}
