package org.wildfly.undertow.quickstart;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * This is a simple Hello World server using Async IO(non-blocking handler)
 * 
 * Access 'http://localhost:8080' from a browser the "Hello World" display in page
 */
public class HelloWorldServer {

	public static void main(String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(new HttpHandler(){

					public void handleRequest(HttpServerExchange exchange)throws Exception {
						exchange.getRequestHeaders().put(Headers.CONTENT_TYPE, "text/plain");
						exchange.getResponseSender().send("Hello World");
					}
				}).build();
		server.start();
	}

}
