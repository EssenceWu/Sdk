package com.ycw.sdk.configuration;

import com.ycw.sdk.websocket.WebSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration("WebSocketService")
public class WebSocketService {
    @Value("${server.netty.boss}")
    private int bosser;

    @Value("${server.netty.worker}")
    private int worker;

    @Value("${server.netty.backlog}")
    private int backlog;

    @Value("${server.netty.port}")
    private int port;

    @Autowired
    private WebSocketChannel webSocketChannel;

    @Bean(value = "WebSocketBootStrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bosserGroup = new NioEventLoopGroup(bosser);
        EventLoopGroup workerGroup = new NioEventLoopGroup(worker);
        try {
            b.group(bosserGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.option(ChannelOption.SO_BACKLOG, backlog);
            b.option(ChannelOption.SO_REUSEADDR, true);
            b.childHandler(webSocketChannel);
            b.bind(port).sync();
        } catch (InterruptedException e) {
        }
        return b;
    }
}
