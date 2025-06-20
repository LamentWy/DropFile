package com.lament.z.drop.netty.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lament.z.drop.netty.common.codec.DropFileDecoder;
import com.lament.z.drop.netty.server.handler.ReceiveFileHandler;
import com.lament.z.drop.util.SystemUtil;
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
	private static final int SERVER_PORT = 9332;
	public static final String MAC = "/Downloads/drop/";
	public static final String WINDOWS = "\\Downloads\\drop\\";
	private String defaultDir;
	private volatile boolean status = false;
	private final EventLoopGroup boss = new NioEventLoopGroup();
	private final EventLoopGroup worker = new NioEventLoopGroup();
	private Channel channel;
	private static final InnerServer innerServer = new InnerServer();
	private InnerServer() {
		initDefaultDir();
	}
	private void initDefaultDir() {

		log.info("OS: [{}]", SystemUtil.getOSType());
		if (SystemUtil.IS_MAC){
			this.defaultDir = SystemUtil.USER_DIR + MAC;
		}
		if (SystemUtil.IS_WINDOWS){
			this.defaultDir = SystemUtil.USER_DIR + WINDOWS;
		}

		if (SystemUtil.getOSType().equals("Unknown")){
			log.warn("Un supported system type: {} | DropFile Exit.",SystemUtil.OS_NAME);
			System.exit(-1);
		}
		Path path = Paths.get(this.defaultDir);
		if (!Files.isDirectory(path)){
			try {
				Files.createDirectories(path);
			}
			catch (IOException e) {
				log.error("Can't create Dir. Reason: {}",e.getMessage());
				Thread.currentThread().interrupt();
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

	/**
	 * @return true:server is running. false: server stop.
	 * */
	public boolean run(){
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss,worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) {
						ch.pipeline().addLast(new DropFileDecoder(),
								new ReceiveFileHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG,1024)
				.childOption(ChannelOption.SO_KEEPALIVE,false);

		ChannelFuture future;
		try {
			future = serverBootstrap.bind(SERVER_PORT).sync();
			this.channel = future.channel();

			this.channel.closeFuture().sync();
		}
		catch (InterruptedException e) {
			if (log.isDebugEnabled()){
				log.debug("InnerServer.run() | ", e);
			}
			log.error("InnerServer start failed, DropFile exit. | {}",e.getMessage());
			Thread.currentThread().interrupt();
		}finally {
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
		return this.status;
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
