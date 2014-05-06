package org.jboss.msc.quickstart.container;

import java.util.concurrent.TimeUnit;

/**
 * 
 *  java -cp target/msc-quickstart.jar -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=y org.jboss.msc.quickstart.container.ContainerExecutorTest
 * 
 * @author kylin
 *
 */
public class ContainerExecutorTest {

	public static void main(String[] args) {
		
		ContainerExecutor executor = new ContainerExecutor(2, 4, 2, TimeUnit.MINUTES);
		for (int i = 0 ; i < 100 ; i ++) {
			executor.execute(new Task(i + 1));
		}
		
		System.out.println("DONE");
	}
	
	static class Task implements Runnable {

		private int id;
		
		public Task(int id) {
			super();
			this.id = id;
		}

		public void run() {
			System.out.println("execute  task " + id);
		}
		
	}

}
