package org.jboss.modules.export.c;

public class Cast {

	public void cast(){
		System.out.println("JBoss Module Export [A -> B -> C]");
		loadclass("C");
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
