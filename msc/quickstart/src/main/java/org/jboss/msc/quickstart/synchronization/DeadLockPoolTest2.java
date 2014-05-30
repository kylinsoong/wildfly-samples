package org.jboss.msc.quickstart.synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.synchronization.DeadLockPoolTest2
 */
public class DeadLockPoolTest2 {

	@SuppressWarnings("static-access")
	public static void sleep(long time) {
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {

		final Object A = new Object();
		final Object B = new Object();

		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.execute(new Runnable() {

			public void run() {

				Thread.currentThread().setName("thread-one");

				synchronized (A) {
					sleep(1000);
					synchronized (B) {
					}
				}

			}
		});

		executor.execute(new Runnable() {

			public void run() {

				Thread.currentThread().setName("thread-two");

				synchronized (B) {
					sleep(1000);
					synchronized (A) {
					}
				}
			}
		});

	}

}
