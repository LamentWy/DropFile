package com.lament.z.drop.netty.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lament.z.drop.netty.common.codec.DropFileDecoder;
import com.lament.z.drop.netty.server.handler.ReceiveFileHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * receive and storage files
 * */
public class InnerServer {
	Logger log = LoggerFactory.getLogger(InnerServer.class);
	// default port
	private final int port = 9332;

	private static final String MAC = "/Downloads/drop/";
	private static final String WINDOWS = "\\Downloads\\drop\\";

	private String defaultDir = System.getProperty("user.home");
	private final EventLoopGroup boss = new NioEventLoopGroup();
	private final EventLoopGroup worker = new NioEventLoopGroup();
	private Channel channel;
	private static final InnerServer innerServer = new InnerServer();
	private InnerServer() {
		String osName = System.getProperty("os.name");
		log.info("OS: [{}]",osName);
		if (osName.contains("Mac")){
			this.defaultDir = defaultDir + MAC;
		}
		if (osName.contains("Windows")){
			this.defaultDir = defaultDir + WINDOWS;
		}
		Path path = Paths.get(this.defaultDir);
		if (!Files.isDirectory(path)){
			try {
				Files.createDirectories(path);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static InnerServer getServer(){
		return innerServer;
	}

	public String getDefaultDir(){
		return this.defaultDir;
	}

	public void setDefaultDir(String dir){
		this.defaultDir = dir;
	}
	public void run(){
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss,worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new DropFileDecoder(),
								new ReceiveFileHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG,1024)
				.childOption(ChannelOption.SO_KEEPALIVE,false);

		ChannelFuture future = null;
		try {
			future = serverBootstrap.bind(port).sync();
			this.channel = future.channel();
			this.channel.closeFuture().sync();
		}
		catch (InterruptedException e) {
			log.error(" Exception: {}",e.getMessage());
			throw new RuntimeException(e);
		}finally {
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

	public void shutdown(){
		if (log.isDebugEnabled()){
			log.debug("InnerServer shutdown.");
		}
		if (this.channel != null){
			this.channel.close();
		}
		worker.shutdownGracefully();
		boss.shutdownGracefully();
	}

}
