package com.ycw.sdk.websocket;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        try {
            if (textWebSocketFrame.text().equals("PING")) {
                ctx.channel().writeAndFlush(new TextWebSocketFrame("PONG"));
            } else if (textWebSocketFrame.text().equals("PONG")) {
                ctx.channel().writeAndFlush(new TextWebSocketFrame("PING"));
            } else {
                WebSocketTemplate webSocketTemplate = JSONObject.parseObject(textWebSocketFrame.text(), WebSocketTemplate.class);
                if (webSocketTemplate.getData() == null) {
                    WebSocketSession.setSession(webSocketTemplate.getToken(), ctx.channel());
                } else {
                    WebSocketSession.send(webSocketTemplate);
                }
            }
        } catch (Exception e) {
            ctx.channel().close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        WebSocketSession.removeSession(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                ctx.channel().close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();
    }
}
