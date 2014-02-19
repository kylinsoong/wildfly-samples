package org.wildfly.undertow.quickstart.chat;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import org.xnio.ChannelListener;

public class ChatServer {
	
	private static final List<WebSocketChannel> sessions = new ArrayList<WebSocketChannel>();

	public static void main(String[] args) {
		
		Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
				                            .setHandler(Handlers.path().addPrefixPath("/myapp", Handlers.websocket(new WebSocketConnectionCallback(){

												public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {

													synchronized (sessions) {
														sessions.add(channel);
														channel.getCloseSetter().set(new ChannelListener<Channel>() {
					                                        @Override
					                                        public void handleEvent(Channel channel) {
					                                            synchronized (sessions) {
					                                                sessions.remove(channel);
					                                            }
					                                        }
					                                    });
					                                    channel.getReceiveSetter().set(new AbstractReceiveListener() {

					                                        @Override
					                                        protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
					                                            final String messageData = message.getData();
					                                            synchronized (sessions) {
					                                                for (WebSocketChannel session : sessions) {
					                                                    WebSockets.sendText(messageData, session, null);
					                                                }
					                                            }
					                                        }
					                                    });
					                                    channel.resumeReceives();
													}
													
												}}))
											  .addPrefixPath("/", Handlers.resource(new ClassPathResourceManager(ChatServer.class.getClassLoader(),ChatServer.class.getPackage())).addWelcomeFiles("index.html"))).build();
		server.start();
		
	}

}
