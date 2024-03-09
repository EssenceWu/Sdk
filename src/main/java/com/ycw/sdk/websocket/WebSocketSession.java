package com.ycw.sdk.websocket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSession {
    private static final Map<String, Channel> sessionManager = new ConcurrentHashMap<>();

    public static Channel getSession(String token) {
        return sessionManager.get(token);
    }

    public static void setSession(String token, Channel channel) {
        Channel ch = getSession(token);
        if (ch != null && !ch.equals(channel)) {
            WebSocketAttribute.setAttribute(null, ch);
            ch.close();
        }
        WebSocketAttribute.setAttribute(token, channel);
        sessionManager.put(token, channel);
    }

    public static void removeSession(Channel channel) {
        String token = WebSocketAttribute.getAttribute(channel);
        if (token != null) {
            sessionManager.remove(token);
        }
    }

    public static void send(WebSocketTemplate webSocketTemplate) {
        Channel ch = getSession(webSocketTemplate.getToken());
        if (ch != null) {
            ch.writeAndFlush(new TextWebSocketFrame(webSocketTemplate.getData()));
        }
    }
}
