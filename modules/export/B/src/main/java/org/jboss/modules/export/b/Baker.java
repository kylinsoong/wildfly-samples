package org.jboss.modules.export.b;

import org.jboss.modules.export.c.Cast;

public class Baker {

	public void baker() {
		new Cast().cast();
		loadclass("B");
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
