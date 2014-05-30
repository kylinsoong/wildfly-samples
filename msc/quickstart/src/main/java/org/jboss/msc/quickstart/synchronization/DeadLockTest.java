package org.jboss.msc.quickstart.synchronization;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.DeadLockTest
 */
public class DeadLockTest {

	public static void main(String[] args) {
		
		final A a = new A();
		final B b = new B();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				a.foo(b);
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				b.foo(a);
			}}).start();

	}

}


