package org.jboss.jmx.test;

public class Test implements TestMBean {

	public String Message;

	public Test() {
		System.out.println(" TestMBean is activated...inside Test() constructor");
	}

	public void setMessage(String Message) {
		System.out.println(" Server Watch Message is set to : " + Message);
		this.Message = Message;
	}

	public String getMessage() {
		System.out.println(" Server Watch Message is set to : " + Message);
		return this.Message;
	}

	public void start() throws Exception {
		System.out.println(" Starting start() Test invoked");
		Class cls = Class.forName("org.jboss.modules.layers.Cast").newInstance().getClass();  
        System.out.println(cls + " are accessible"); 
        cls.getMethod("cast", new Class[]{}).invoke(cls.newInstance(), new Object[]{});
		
	}

	public void stop() throws Exception {
		System.out.println(" Stopping stop() Test  invoked");
	}

}
