package org.wildfly.undertow.quickstart;

import org.xnio.Xnio;

/**
 * 1. Create an XNIO Worker
 * 2. Create an XNIO SSL instance (optional)
 * 3. Create an instance of the relevant Undertow listener class
 * 4. Open a server socket using XNIO and set its accept listener
 */
public class AssemblingServer {

	public static void main(String[] args) {
		
		Xnio xnio = Xnio.getInstance();
		
	}

}
