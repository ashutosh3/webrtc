package com.vroom.message.handler;

import java.nio.charset.Charset;
import java.util.Calendar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf in = (ByteBuf) msg;
    	int response = -1;
    	try {
    		String command = in.toString(CharsetUtil.UTF_8);
    		System.out.println("Command " + command);
    		char cmd = command.charAt(0);
    		Calendar now = Calendar.getInstance();
    		if ('y' == cmd) {
				response = now.get(Calendar.YEAR);
			} else if ('m' == cmd) {
				response = now.get(Calendar.MONTH);
			} else if ('d' == cmd) {
				response = now.get(Calendar.DAY_OF_MONTH);
			}
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	}
    	
    	ByteBuf resp = ctx.alloc().buffer(10);
    	if (response != -1) {
    		resp.writeInt(response);
    	}
    	resp.writeCharSequence("Result: " + response, Charset.defaultCharset());
    	ctx.writeAndFlush(resp);
    }
    

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
