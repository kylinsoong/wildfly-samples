package org.jboss.modules.export.c;

public class Cast {

	public Object cast(){
	    System.out.println(" -> C");
		System.out.println(this.getClass().getClassLoader());
		System.out.println("C -> \n");
		return this;
	}
	
}
