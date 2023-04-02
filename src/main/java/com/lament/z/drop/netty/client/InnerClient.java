package com.lament.z.drop.netty.client;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lament.z.drop.netty.client.handler.DropFileHandler;
import com.lament.z.drop.netty.common.codec.DropFileEncoder;
import com.lament.z.drop.netty.pojo.DropFile;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InnerClient {

	Logger log = LoggerFactory.getLogger(InnerClient.class);
	private final String targetHost;
	private final int port;

	public InnerClient(String targetHost, int port) {
		this.targetHost = targetHost;
		this.port = port;
	}


	public void sendFile(DropFile dropFile) {
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) {
						ch.pipeline().addLast(new DropFileEncoder(),
								new DropFileHandler(dropFile));
					}
				})
				.option(ChannelOption.AUTO_CLOSE, true);

		try {
			ChannelFuture future = bootstrap.connect(targetHost, port).sync();
			future.channel().closeFuture().sync();

		}
		catch (InterruptedException e) {
			log.error("sendFile | InterruptedException : {}", e.getMessage());
		}
		finally {
			worker.shutdownGracefully();
		}
	}

	public void sendFile(String fullName) {
		DropFile file = new DropFile();
		Path path = Paths.get(fullName);
		file.setFileName(path.getFileName().toString());
		try {
			file.setPayload(Files.readAllBytes(path));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.sendFile(file);
	}
}
