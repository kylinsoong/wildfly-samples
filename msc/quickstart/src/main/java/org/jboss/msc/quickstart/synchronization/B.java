package org.jboss.msc.quickstart.synchronization;

public class B {

	@SuppressWarnings("static-access")
	public void foo(A a) {
		System.out.println(Thread.currentThread().getName() + " locking B monitor");
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " locked B monitor");
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
			}
			a.foo(this);
		}
	}
}
