package org.jboss.msc.quickstart.synchronization;

/**
 * 
 * How to run
 *   java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.SynchronizedMethodTest
 * 
 * @author kylin
 *
 */
public class SynchronizedMethodTest {

	synchronized void method1() {
		method2();
	}

	synchronized void method2() {
		method3();
	}

	synchronized void method3() {
		try {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
