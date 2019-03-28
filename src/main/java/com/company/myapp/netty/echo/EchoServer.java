package com.company.myapp.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Echoes back any received data from a client.
 */
public final class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoServerHandler serverHandler = new EchoServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            // 绑定线程池
            b.group(bossGroup, workerGroup)
                // 指定使用的channel
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                // 绑定客户端连接时候触发操作
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("connected...; Client:" + ch.remoteAddress());
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            // 客户端触发操作
                            p.addLast(sslCtx.newHandler(ch.alloc()));
                        }
                        //p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(serverHandler);
                    }
                });

            // Start the server.
            // 服务器异步创建绑定，绑定监听端口
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println(EchoServer.class + " started and listen on " + f.channel().localAddress());

            // Wait until the server socket is closed.
            // 关闭服务器通道
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            // 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
