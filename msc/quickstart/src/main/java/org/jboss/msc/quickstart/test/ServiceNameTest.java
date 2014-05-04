package org.jboss.msc.quickstart.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.msc.service.ServiceName;

public class ServiceNameTest {

	public static void main(String[] args) {
		ServiceName a = ServiceName.JBOSS.append("one");
		ServiceName b = ServiceName.JBOSS.append("one", "two");
		List<ServiceName> list = new ArrayList<ServiceName>();
		list.add(b);
		list.add(a);
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
		
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
