package org.wildfly.perf.concurrency;

public class KylinSoong {
	
	private static KylinSoong instance = null;
	
	private KylinSoong() {
		System.out.println("KylinSoong Constructed");
	}
	
	public static KylinSoong getInstance() {
		
		synchronized(KylinSoong.class){
			if (null == instance) {
				instance = new KylinSoong();
			}
		}
		
		return instance;
	}
	
	public synchronized void foo() {
		System.out.println("foo");
		sleep();
	}
	
	public void bar() {
		System.out.println("bar");
		sleep();
	}

	public void sleep() {
		long limit = 600851475143L;
		for (long number = 2; number < limit; number++){
			long s = number - 1 ;
		}
	}
	
	public static void  main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		
		System.out.println(System.currentTimeMillis() - start);
	}
}
