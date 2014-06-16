package org.jboss.msc.quickstart.threads;

import java.util.concurrent.TimeUnit;

public class App {
	
	public static void main(String[] args) {
		
		MyThreadPoolExecutor executor = new MyThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS);
		
		Runnable command = new Runnable() {

			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + " start");
					Thread.sleep(1000 * 600);
					System.out.println(Thread.currentThread().getName() + " exit");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for(int i = 0 ; i < 10 ; i ++) {
			executor.execute(command);
		}
		
		System.out.println("main thread exit");
		
	}

}
