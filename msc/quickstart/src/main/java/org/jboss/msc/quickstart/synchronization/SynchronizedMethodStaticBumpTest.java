package org.jboss.msc.quickstart.synchronization;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.SynchronizedMethodStaticBumpTest
 */
public class SynchronizedMethodStaticBumpTest {

	static void method1() {
		synchronized(SynchronizedMethodStaticBumpTest.class) {
			method2();
		}
	}

	static void method2() {
		synchronized(SynchronizedMethodStaticBumpTest.class) {
			method3();
		}
	}

	static void method3() {
		
		synchronized(SynchronizedMethodStaticBumpTest.class) {
			try {
				Thread.currentThread().sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
			}
		}
		
	}

	public static void main(String[] args) {
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				SynchronizedMethodStaticBumpTest.method1();
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				SynchronizedMethodStaticBumpTest.method1();
				
			}}).start();
	}

}
