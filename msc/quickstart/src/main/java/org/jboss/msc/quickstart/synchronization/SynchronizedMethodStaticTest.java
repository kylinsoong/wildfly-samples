package org.jboss.msc.quickstart.synchronization;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.SynchronizedMethodStaticTest
 */
public class SynchronizedMethodStaticTest {

	static synchronized void method1() {
		method2();
	}

	static synchronized void method2() {
		method3();
	}

	static synchronized void method3() {
		try {
			Thread.currentThread().sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				SynchronizedMethodStaticTest.method1();
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				SynchronizedMethodStaticTest.method1();
				
			}}).start();
	}

}
