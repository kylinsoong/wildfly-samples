package org.jboss.msc.quickstart.threads;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryTest {

	public static void main(String[] args) throws InterruptedException {
		
		ThreadFactory threadFactory = new MyThreadFactory();
		
		Thread thread = threadFactory.newThread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					
				}
			}
			
		});
		
		thread.start();
	}
	
	

}


