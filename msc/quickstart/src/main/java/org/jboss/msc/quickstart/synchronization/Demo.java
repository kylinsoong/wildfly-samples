package org.jboss.msc.quickstart.synchronization;

public class Demo {

	public synchronized void foo(){}
	
	public synchronized void zoo() {}
	
	public static synchronized void bar() {}
	
}

class DemoInter {
		
	public void foo(){synchronized(this) {}}
	
	public void zoo() {synchronized(this) {}}
	
	public static void bar() {synchronized(Demo.class) {}}
}
