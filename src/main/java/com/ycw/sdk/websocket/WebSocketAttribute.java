package com.ycw.sdk.websocket;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class WebSocketAttribute {
    private static final String attributeKey = "SESSION_ID";

    private static final AttributeKey<String> sessionId = AttributeKey.valueOf(attributeKey);

    public static String getAttribute(Channel channel) {
        Attribute<String> attribute = channel.attr(sessionId);
        return attribute.get();
    }

    public static void setAttribute(String token, Channel channel) {
        Attribute<String> attribute = channel.attr(sessionId);
        attribute.compareAndSet(null, token);
    }
}
