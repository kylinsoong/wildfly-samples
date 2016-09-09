package org.wildfly.build.example.b;

import org.wildfly.build.example.c.Cast;

public class Baker {

	public Object baker() {
	    new Cast().cast();
		return this;
	}
	
}
