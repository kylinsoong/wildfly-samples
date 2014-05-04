package org.jboss.msc.quickstart.test;

import org.jboss.msc.service.ServiceName;

public class ServiceNameTreeTest {

	public static void main(String[] args) {
		
		ServiceName root = ServiceName.JBOSS;
		
		ServiceName l1a = root.append("level-1-a");
		ServiceName l1b = root.append("level-1-b");
		
		ServiceName l2a = l1a.append("level-2-a");
		ServiceName l2b = l1a.append("level-2-b");
		
		System.out.println(l2a);
		System.out.println(l2a.getParent());
		System.out.println(l2a.getParent().getParent());
	}

}
