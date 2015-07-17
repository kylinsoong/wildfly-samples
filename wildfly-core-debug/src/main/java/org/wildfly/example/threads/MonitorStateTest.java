package org.wildfly.example.threads;

public class MonitorStateTest {

	public static void main(String[] args) throws InterruptedException {

		new MonitorStateTest().test();
	}

	private void test() throws InterruptedException {

		synchronized(this) {
			wait();
		}
	}

}
