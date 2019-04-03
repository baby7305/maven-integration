package com.company.myapp.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.  Simply put, the echo client initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClient {

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer size = 256;

    public void connect(String host, int port) throws Exception {

        boolean ssl = System.getProperty("ssl") != null;

        // Configure SSL.git
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 注册线程池
            b.group(group)
                // 使用NioSocketChannel来作为连接用的channel类
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                // 绑定连接初始化器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("开始连接");
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                        }
                        //p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new EchoClientHandler());
                    }
                });

            System.out.println("初始化");

            // Start the client.
            // 异步连接服务器
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("连接完成");

            // Wait until the connection is closed.
            // 异步等待关闭连接channel
            f.channel().closeFuture().sync();
            System.out.println("关闭完成");
        } finally {
            // Shut down the event loop to terminate all threads.
            // 释放线程池资源
            group.shutdownGracefully();
        }
    }
}
