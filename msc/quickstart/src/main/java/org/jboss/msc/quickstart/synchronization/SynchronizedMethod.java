package org.jboss.msc.quickstart.synchronization;

public class SynchronizedMethod {
	
	public static void getMehodName() throws Exception {
		synchronized(SynchronizedMethod.class) {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		}
	}
	
	public void foo() throws Exception {
		synchronized(this) {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		}
	}
	
	public void zoo() throws Exception {
		synchronized(this) {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		}
	}
	
	public void park() {
		System.out.println("This is Park");
	}
}
