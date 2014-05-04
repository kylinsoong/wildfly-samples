package org.jboss.msc.quickstart.container;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolDemo {
	
	public static void main(String[] args) throws InterruptedException {
		new PoolDemo().poolTest();
	}
	
	private void poolTest() {
		
		final boolean wantExceptionOnReject = false;
		
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100, true);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, queue);
		
		for(long i = 0; i < 100 ; ++i) {
			Task t = new Task(String.valueOf(i));
			System.out.println(Thread.currentThread().getName() + " submitted " + t + ", queue size = " + executor.getQueue().size());
			try {
				executor.execute(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	class RejectedHandler implements RejectedExecutionHandler {

		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			System.err.println(Thread.currentThread().getName() + " execution rejected: " + r); 
		}
		
	}
	
	class Task implements Runnable {
		
		private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    private String name;
	    private Date created;
	    
		public Task(String name) {
			this.name = name;
			this.created = new Date();
		}

		public void run() {
			final boolean wantOverflow = true;
			System.out.println(Thread.currentThread().getName() + " executing " + this);
			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " executed " + this);
		}
		
		public String toString() {
			return name + ", created " + fmt.format(created);
		}
		
	}

}
