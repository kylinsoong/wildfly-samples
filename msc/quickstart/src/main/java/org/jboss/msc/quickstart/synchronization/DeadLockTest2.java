package org.jboss.msc.quickstart.synchronization;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.DeadLockTest2
 */
public class DeadLockTest2 {
	
	@SuppressWarnings("static-access")
	public static void sleep(long time){
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		
		final Object A = new Object();
		final Object B = new Object();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-one");
				
				synchronized(A) {
					sleep(1000);
					synchronized (B) {}
				}

			}}).start();
		
		new Thread(new Runnable(){

			public void run() {
				
				Thread.currentThread().setName("thread-two");
				
				synchronized (B) {
					sleep(1000);
					synchronized(A) {}
				}

			}}).start();
	}

}
