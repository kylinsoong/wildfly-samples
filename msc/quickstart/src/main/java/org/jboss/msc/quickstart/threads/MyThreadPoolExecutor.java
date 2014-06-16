package org.jboss.msc.quickstart.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {
	
	static ThreadPoolExecutor.CallerRunsPolicy POLICY = new ThreadPoolExecutor.CallerRunsPolicy();
	
	static ThreadFactory threadFactory = new MyThreadFactory();
	
	static LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable> ();

	public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory, POLICY);
	}

}
