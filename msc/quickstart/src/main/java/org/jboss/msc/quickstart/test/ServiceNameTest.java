package org.jboss.msc.quickstart.test;

import org.jboss.msc.service.ServiceName;

public class ServiceNameTest {

	public static void main(String[] args) {
		
		System.out.println(ServiceName.of("one"));
		System.out.println(ServiceName.of("one", "two"));
		System.out.println(ServiceName.of("one", "two", "three"));
		
		System.out.println(ServiceName.of("one", "two", "three").getParent());
		System.out.println(ServiceName.of("one", "two", "three").getParent().getParent());
		System.out.println(ServiceName.of("one", "two", "three").getParent().getParent().getParent());
		
		System.out.println(ServiceName.of("one", "two", "three").getSimpleName());
		
		System.out.println(ServiceName.JBOSS);
		System.out.println(ServiceName.JBOSS.append(ServiceName.of("one", "two", "three")));
		
		System.out.println(ServiceName.of("one").compareTo(ServiceName.of("one")));
		System.out.println(ServiceName.of("one").compareTo(ServiceName.of("one", "two")));
		System.out.println(ServiceName.of("one", "two").compareTo(ServiceName.of("one")));
		
		System.out.println(ServiceName.JBOSS.append(ServiceName.of("one", "two")).equals(ServiceName.parse("jboss.one.two")));
		
		System.out.println(ServiceName.JBOSS.append("one", "two").hashCode() == ServiceName.parse("jboss.one.two").hashCode());
	}

}
