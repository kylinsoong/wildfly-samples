package org.jboss.msc.quickstart.container;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ContainerExecutor extends ThreadPoolExecutor {
	
	private static final ThreadPoolExecutor.CallerRunsPolicy POLICY = new ThreadPoolExecutor.CallerRunsPolicy();
	
	private static final AtomicInteger executorSeq = new AtomicInteger(1);
	private static final Thread.UncaughtExceptionHandler HANDLER = new Thread.UncaughtExceptionHandler(){
		public void uncaughtException(Thread t, Throwable e) {
			
		}		
	};

	ContainerExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){
			private final int id = executorSeq.getAndIncrement();
			private final AtomicInteger threadSeq = new AtomicInteger(1);
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
                thread.setName(String.format("MSC service thread %d-%d", Integer.valueOf(id), Integer.valueOf(threadSeq.getAndIncrement())));
                thread.setUncaughtExceptionHandler(HANDLER);
                return thread;
			}},POLICY);
	}
}
