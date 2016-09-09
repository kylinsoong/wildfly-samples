package org.wildfly.build.example.a;

import org.wildfly.build.example.b.Baker;

public class Afirm {
	
	public void afirm() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		new Baker().baker();
	}

	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	    new Afirm().afirm();
	}
}
