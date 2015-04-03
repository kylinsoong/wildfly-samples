package org.jboss.dmr;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class testModelNode {
	
	ModelNode node;
	
	testModelNode() {
		node = new ModelNode();
		node.get("description").set("A managable resource");
		node.get("type").set(ModelType.OBJECT);
		node.get("tail-comment-allowed").set(false);
		node.get("attributes").get("foo").set("some description of foo");
		node.get("attributes").get("bar").set("some description of bar");
		node.get("attributes").get("list").add("value1");
		node.get("attributes").get("list").add("value2");
        node.get("attributes").get("list").add("value3");
        node.get("value-type").get("size").set(ModelType.INT);
        node.get("value-type").get("color").set(ModelType.STRING);
        node.get("big-decimal-value").set(BigDecimal.valueOf(10.0));
        node.get("big-integer-value").set(BigInteger.TEN);
        node.get("bytes-value").set(new byte[] { (byte) 0, (byte) 55 });
        node.get("double-value").set(Double.valueOf("55"));
        node.get("max-double-value").set(Double.MAX_VALUE);
        node.get("int-value").set(Integer.valueOf("12"));
        node.get("max-int-value").set(Integer.MAX_VALUE);
        node.get("long-value").set(Long.valueOf("14"));
        node.get("max-long-value").set(Long.MAX_VALUE);
        node.get("property-value").set("property", ModelType.PROPERTY);
        node.get("expression-value").set(new ValueExpression("$expression"));
        node.get("true-value").set(true);
        node.get("false-value").set(false);

	}
	
	public void test() {
		
		//ToString
		System.out.println(node.toString());
		
		//ToJSONString
		System.out.println(node.toJSONString(false));
		
		//OutputDMRString
		StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter, true);
        node.writeString(writer, false);
        System.out.println(stringWriter.toString());
        
        //FormatAsJSON
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter, true);
        node.formatAsJSON(writer, 0, true);
        System.out.println(stringWriter.toString());
        
        //OutputJSONString
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter, true);
        node.writeJSONString(writer, false);
        System.out.println(stringWriter.toString());

        
	}
	
	public void testRecursiveHas() {
		String[] names = {"a", "b", "c"};

        ModelNode testee = new ModelNode();
        
        System.out.println(testee.has(names));
        
        testee.setEmptyList();
        System.out.println(testee.has(names));
        
        testee.set(1);
        System.out.println(testee.has(names));
        
        testee.setEmptyObject();
        System.out.println(testee.has(names));
        
        testee.get("a", "b", "d");
        System.out.println(testee.has(names));
        
        testee.get("a", "b", "c", "d");
        System.out.println(testee);
        System.out.println(testee.has(names));
        
        testee.get("a").set("b", "d");
        System.out.println(testee);
        System.out.println(testee.has(names));
	}

	public static void main(String[] args) {
		new testModelNode().testRecursiveHas();
	}

}
