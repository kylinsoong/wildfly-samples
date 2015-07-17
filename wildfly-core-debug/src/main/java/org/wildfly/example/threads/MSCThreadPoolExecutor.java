package org.wildfly.example.threads;

import static java.security.AccessController.doPrivileged;

import java.security.PrivilegedAction;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class used to simulate MSC ThreadPoolExecutor initialize
 * 
 * @author kylin
 *
 */
public class MSCThreadPoolExecutor {
	
	private static final AtomicInteger executorSeq = new AtomicInteger(1);
	private static final Thread.UncaughtExceptionHandler HANDLER = new Thread.UncaughtExceptionHandler() {

		public void uncaughtException(Thread t, Throwable e) {
			System.err.println(t.getName() + " - " + e);
			e.printStackTrace();
		}};
	private static final ThreadPoolExecutor.CallerRunsPolicy POLICY = new ThreadPoolExecutor.CallerRunsPolicy();

	public static void main(String[] args) throws Exception {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){

			private final int id = executorSeq.getAndIncrement();
            private final AtomicInteger threadSeq = new AtomicInteger(1);
            
			public Thread newThread(Runnable r) {
				return doPrivileged(new ThreadAction(r, id, threadSeq));
			}}, POLICY);
		
		for(int i = 0 ; i < 10 ; i ++) {
			executor.execute(new WaitTask());
		}
				
		System.out.println(executor);
		
//		Thread.sleep(Long.MAX_VALUE);
	}
	
	static class WaitTask implements Runnable {

		@Override
		public void run() {
			synchronized(this){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class PrintTask implements Runnable{
		public void run() {
			System.out.println("This is Print Task");
		}
	}
	
	static class ThreadAction implements PrivilegedAction<Thread> {

        private final Runnable r;
        private final int id;
        private final AtomicInteger threadSeq;

        ThreadAction(final Runnable r, final int id, final AtomicInteger threadSeq) {
            this.r = r;
            this.id = id;
            this.threadSeq = threadSeq;
        }

        public Thread run() {
        	Thread thread = new Thread(r);
            thread.setName(String.format("MSC service thread %d-%d", Integer.valueOf(id), Integer.valueOf(threadSeq.getAndIncrement())));
            thread.setUncaughtExceptionHandler(HANDLER);
            return thread;
        }
    }

}
