package org.wildfly.domain.test.exter;

import java.io.IOException;

/*
 * 1 --> 129
 * 2 --> 130
 * 3 --> NO
 * 4 --> JVM Crash with 134
 * 5 --> 133
 * 6 --> 134
 * 7 --> JVM Crash with 134
 * 8 --> JVM Crash with 134
 * 9 --> 137
 * 10 --> 138
 * 
 * 
 */
public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("External Process Start");
		
		Thread.sleep(Long.MAX_VALUE) ;
		
		System.out.println("External Process Exit");
		
		Runtime.getRuntime().exit(0);
	}

}
