package org.jboss.modules.export.a;

import org.jboss.modules.export.b.Baker;

public class Afirm {
	
	public void afirm() {
		new Baker().baker();
		loadclass("A");
	}

	private void loadclass(String flag) {
		try {
			Class cls = Class.forName("org.jboss.modules.export.c.Cast").newInstance().getClass();
			System.out.println(flag + " -> " + cls);
		} catch (Exception e) {
			System.out.println(flag + " Error " + e.getMessage());
		}
	}
}
