package com.vroom.message.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ctx.writeAndFlush(msg);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
    	final ByteBuf time = ctx.alloc().buffer(4);
    	time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    	final ChannelFuture f = ctx.writeAndFlush(time);
    	
    	f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
    	
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
