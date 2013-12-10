package org.jboss.jmx.test;

public class Test implements TestMBean {

	boolean flag = true;
	public String Message;

	public Test() {
		System.out.println(" TestMBean is activated...inside Test() constructor--setting default Message=Hello");
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
		System.out.println("Starting start() Test invoked");
		Message = "JBoss";
	}

	public void stop() throws Exception {
		System.out.println("Stopping stop() Test  invoked");
	}

}
