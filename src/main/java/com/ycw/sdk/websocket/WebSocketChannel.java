package com.ycw.sdk.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannel extends ChannelInitializer<SocketChannel> {
    @Value("${server.netty.length}")
    private int length;

    @Value("${server.netty.url}")
    private String url;

    @Value("${server.netty.readIdle}")
    private int readIdle;

    @Value("${server.netty.writeIdle}")
    private int writeIdle;

    @Value("${server.netty.allIdle}")
    private int allIdle;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new ChunkedWriteHandler());
        p.addLast(new HttpObjectAggregator(length));
        p.addLast(new WebSocketServerProtocolHandler(url));
        p.addLast(new IdleStateHandler(readIdle, writeIdle, allIdle));
        p.addLast(webSocketHandler);
    }
}
