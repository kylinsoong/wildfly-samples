package org.wildfly.example;

import static java.security.AccessController.doPrivileged;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.threads.JBossThreadFactory;
import org.wildfly.security.manager.action.GetAccessControlContextAction;

public class ServerExecutorServiceExample {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {

		final ThreadGroup threadGroup = new ThreadGroup("ServerService ThreadGroup");
        final String namePattern = "ServerService Thread Pool -- %t";
        final ThreadFactory threadFactory = new JBossThreadFactory(threadGroup, Boolean.FALSE, null, namePattern, null, null, doPrivileged(GetAccessControlContextAction.getInstance()));
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 20L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        executorService.execute(new Runnable(){

			@Override
			public void run() {
				
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
			}});
//        Thread.currentThread().sleep(Long.MAX_VALUE);
        
	}

}
