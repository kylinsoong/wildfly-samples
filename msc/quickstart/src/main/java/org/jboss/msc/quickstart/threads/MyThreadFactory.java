package org.jboss.msc.quickstart.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory {
	
	AtomicInteger threadSeq = new AtomicInteger(1);
	
	Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler(){
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println(t.getName() + " met uncaught exception: " + e.getMessage());
		}		
	};

	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setName(String.format("MSC service thread %d", Integer.valueOf(threadSeq.getAndIncrement())));
		thread.setUncaughtExceptionHandler(handler);
		return thread;
	}

}
