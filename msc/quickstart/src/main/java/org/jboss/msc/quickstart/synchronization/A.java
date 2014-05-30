package org.jboss.msc.quickstart.synchronization;

public class A {
	
	@SuppressWarnings("static-access")
	public void foo(B b) {
		System.out.println(Thread.currentThread().getName() + " locking A monitor");
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " locked A monitor");
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
			}
			b.foo(this);
		}
	}

}
