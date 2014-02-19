package org.wildfly.undertow.quickstart;

public class TestAll {

	public static void main(String[] args) {
		for (int i = 0 ; i < 10000000 ; i ++) {
			System.out.println(Thread.currentThread().getName() + " is runing " + (i + 1));
		}
		System.out.println("DONE");
	}

}
