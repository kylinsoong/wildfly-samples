package org.jboss.msc.quickstart.synchronization;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.SynchronizedMethodTest2
 */
public class SynchronizedMethodTest2 {
	
	
	public static void main(String[] args) {
		
		final SynchronizedMethod method = new SynchronizedMethod();

		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				try {
					SynchronizedMethod.getMehodName();
				} catch (Exception e) {
				}
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				try {
					method.foo();
				} catch (Exception e) {

				}
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-three");
				try {
					method.zoo();
				} catch (Exception e) {
				}
				
			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-four");
				method.park();
				
			}}).start();
		
	}
	

}
