package com.lament.z.drop.netty.client.handler;

import com.lament.z.drop.netty.pojo.DropFile;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropFileHandler extends ChannelInboundHandlerAdapter {
	Logger log = LoggerFactory.getLogger(DropFileHandler.class);
	private final DropFile dropFile;

	public DropFileHandler(DropFile dropFile) {
		this.dropFile = dropFile;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx){
		ChannelFuture channelFuture = ctx.writeAndFlush(dropFile);
		channelFuture.addListener(future -> {
			if (future.isDone()){
				ctx.close();
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		log.error("exception: {}", cause.getMessage());
		ctx.close();
	}
}
