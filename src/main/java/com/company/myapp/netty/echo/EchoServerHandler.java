package com.company.myapp.netty.echo;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("server channelRead...; received:" + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("server channelReadComplete..");
        // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("server occur exception:" + cause.getMessage());
        // Close the connection when an exception is raised.
        // 关闭发生异常的连接
        cause.printStackTrace();
        ctx.close();
    }
}
