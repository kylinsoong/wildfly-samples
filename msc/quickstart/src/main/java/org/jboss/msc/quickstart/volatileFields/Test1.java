package org.jboss.msc.quickstart.volatileFields;

public class Test1 {

	static int i = 0, j = 0;

	static void one() {
		i++;
		j++;
	}

	static void two() {
		System.out.println("i=" + i + " j=" + j);
	}
}
