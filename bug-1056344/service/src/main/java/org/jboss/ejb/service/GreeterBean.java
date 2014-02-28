package org.jboss.ejb.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote (Greeter.class)
public class GreeterBean implements Greeter {

	public String greet(String user) {
		System.out.println("GreeterBean invoked");
		return "Hello " + user + ", have a pleasant day!";
	}
}
