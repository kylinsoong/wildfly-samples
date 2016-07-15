package org.jboss.modules.export.b;

import org.jboss.modules.export.c.Cast;

public class Baker {

	public Object baker() {
	    
	    System.out.println(" -> B");
	    
	    // depend on C
	    new Cast().cast();
	    
	    System.out.println(this.getClass().getClassLoader());
		System.out.println("B -> \n");
		return this;
	}
	
}
