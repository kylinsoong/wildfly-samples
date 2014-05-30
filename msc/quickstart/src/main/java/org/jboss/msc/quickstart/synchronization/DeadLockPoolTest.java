package org.jboss.msc.quickstart.synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.DeadLockPoolTest
 */
public class DeadLockPoolTest {

	public static void main(String[] args) {
		
		final A a = new A();
		final B b = new B();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		executor.execute(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				a.foo(b);
				
			}});
		
		executor.execute(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				b.foo(a);
			}});
	}
	
	
}
