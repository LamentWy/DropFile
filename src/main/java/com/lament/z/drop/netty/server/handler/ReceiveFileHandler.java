package com.lament.z.drop.netty.server.handler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lament.z.drop.netty.pojo.DropFile;
import com.lament.z.drop.netty.server.InnerServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveFileHandler extends ChannelInboundHandlerAdapter {
	Logger log = LoggerFactory.getLogger(ReceiveFileHandler.class);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof DropFile file)) return;

		String fileName = file.getFileName();
		String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		String fullName = InnerServer.getServer().getDefaultDir() + time + fileName;
		long size = file.getPayload().length;
		log.info("file.size: {} bytes, aka {} MB",size,size/ 1000000L);
		Files.write(Paths.get(fullName),file.getPayload());
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error(" exception: {}", cause.getMessage());
		ctx.close();
	}
}
