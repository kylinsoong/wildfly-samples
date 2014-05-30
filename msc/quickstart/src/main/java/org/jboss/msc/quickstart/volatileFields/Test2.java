package org.jboss.msc.quickstart.volatileFields;

public class Test2 {

	static int i = 0, j = 0;

	static void one() {
		
		synchronized(Test2.class)  {
			i ++;
			j++ ;
		}
		
	}

	static void two() {
		
		synchronized(Test2.class) {
			System.out.println("i=" + i + " j=" + j);
		}
		
	}
}
