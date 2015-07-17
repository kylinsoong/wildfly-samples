package org.wildfly.example.threads;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

	public static void main(String[] args) throws Exception {

//		test_1();
		
//		test_2();
		
		test_3();
		
		
	}
	
	static void test_3() throws InterruptedException {
		
		Executor executor = new ThreadPoolExecutor(2, Integer.MAX_VALUE,
                10L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
		
		for(int i = 0 ; i < 10 ; i ++) {
			executor.execute(new PrintTask());
		}
		
		System.out.println(executor);
		
		Thread.sleep(12 * 1000);
		
		System.out.println(executor);
		
	}
	
	static void test_2() throws InterruptedException {
		
		Executor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                10L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
		
		for(int i = 0 ; i < 10 ; i ++) {
			executor.execute(new PrintTask());
		}
		
		System.out.println(executor);
		
		Thread.sleep(12 * 1000);
		
		System.out.println(executor);
		
	}

	static void test_1() {

		Executor executor = Executors.newFixedThreadPool(8);
		
		for(int i = 0 ; i < 10 ; i ++) {
			executor.execute(new PrintTask());
		}
		
		System.out.println(executor);
	}

	public static class PrintTask implements Runnable{

		@Override
		public void run() {
			System.out.println("This is Print Task");
		}
	}

}
