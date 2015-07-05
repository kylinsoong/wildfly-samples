package org.wildfly.example;

import static java.security.AccessController.doPrivileged;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.jboss.threads.JBossThreadFactory;
import org.wildfly.security.manager.action.GetAccessControlContextAction;

public class ScheduledThreadPoolExecutoreExample {

	public static void main(String[] args) {

		final ThreadGroup threadGroup = new ThreadGroup("TestService ThreadGroup");
        final String namePattern = "TestService Thread Pool -- %t";
        final ThreadFactory threadFactory = new JBossThreadFactory(threadGroup, Boolean.FALSE, null, namePattern, null, null, doPrivileged(GetAccessControlContextAction.getInstance()));       
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(4 , threadFactory);
        scheduledExecutorService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        for(int i = 0 ; i < 4 ; i ++) {
        	scheduledExecutorService.scheduleAtFixedRate(new Runnable(){

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis());
				}}, 5, 2, TimeUnit.SECONDS);
        }
	}

}
