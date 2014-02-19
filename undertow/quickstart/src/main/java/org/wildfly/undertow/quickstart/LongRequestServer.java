package org.wildfly.undertow.quickstart;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class LongRequestServer {

	public static void main(String[] args) {

		Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(new HttpHandler(){

			public void handleRequest(HttpServerExchange exchange)throws Exception {
				
				for (int i = 0 ; i < 10000000 ; i ++) {
					System.out.println(Thread.currentThread().getName() + " is runing " + i);
				}
				
				exchange.getRequestHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchange.getResponseSender().send("Long Reuqest Test");
			}
		}).build();
server.start();
	}

}
