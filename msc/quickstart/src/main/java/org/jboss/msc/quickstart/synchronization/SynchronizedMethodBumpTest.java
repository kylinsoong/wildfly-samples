package org.jboss.msc.quickstart.synchronization;

/*
 *  java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.SynchronizedMethodBumpTest
 */
public class SynchronizedMethodBumpTest {
	
	void method1() throws InterruptedException {
		
		synchronized(this) {
			method2();
		}
	}

	void method2() throws InterruptedException {
		synchronized(this) {
			method3();
		}
	}

	void method3() throws InterruptedException {
		
		synchronized(this) {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		}
		
	}

	public static void main(String[] args) {
		
		final SynchronizedMethodTest test = new SynchronizedMethodTest();

		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				test.method1();
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				test.method1();
				
			}}).start();
	
	}

}
