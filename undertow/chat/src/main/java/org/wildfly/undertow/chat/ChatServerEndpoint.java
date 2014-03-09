package org.wildfly.undertow.chat;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class ChatServerEndpoint {

    @OnMessage
    public void chat(String text, Session client) {
    	
    	System.out.println(text);
    	
        client.getAsyncRemote().sendText(text.toUpperCase());
    }
}
