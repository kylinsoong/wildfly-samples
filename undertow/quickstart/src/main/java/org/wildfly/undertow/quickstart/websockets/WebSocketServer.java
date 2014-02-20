package org.wildfly.undertow.quickstart.websockets;

import java.io.IOException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

public class WebSocketServer {

	public static void main(String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
				                            .setHandler(Handlers.path().addPrefixPath("/myapp", Handlers.websocket(new WebSocketConnectionCallback(){

												@Override
												public void onConnect(WebSocketHttpExchange exchange,WebSocketChannel channel) {

													channel.getReceiveSetter().set(new AbstractReceiveListener(){

														@Override
														protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
															WebSockets.sendText(message.getData(), channel, null);
														}});
													
													channel.resumeReceives();
											}
										}))
				                            		.addPrefixPath("/", Handlers.resource(new ClassPathResourceManager(WebSocketServer.class.getClassLoader(),WebSocketServer.class.getPackage())).addWelcomeFiles("index.html")))
				                            .build();
		
		server.start();
	}

}
