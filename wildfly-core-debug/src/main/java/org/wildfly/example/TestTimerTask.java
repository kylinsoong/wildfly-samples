package org.wildfly.example;

import java.util.TimerTask;

public class TestTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println(System.currentTimeMillis());
	}
}
