package org.wildfly.example;

import java.util.Timer;
import java.util.TimerTask;

public class App {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(System.currentTimeMillis());
		Timer timer = new Timer("Test Timer", true);
        TimerTask task = new TestTimerTask();
        timer.schedule(task, 5000, 2000);
        Thread.currentThread().sleep(Long.MAX_VALUE);

	}

}
