package com.conch;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.conch.handler.PacketLengthDecodeHandler;

@Component
public class TcpServerBootstrap {

	private int port = 9999;
	
	protected ServerBootstrap server;
	protected Channel serverFuture;

	@PostConstruct
	public void init() throws InterruptedException {
		
		new Thread(() -> {
			try {
				startServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@PreDestroy
	public void destroyServer() throws InterruptedException {
		serverFuture.close();
	}
	
	private void startServer() throws InterruptedException  {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			server = new ServerBootstrap(); // (2)
			server.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) // (3)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
								@Override
								public void initChannel(SocketChannel ch)
										throws Exception {
									//2 byte legnth를 읽고, length 많큼의 패킷을 만들어 반환해준다!
									ch.pipeline().addLast(new PacketLengthDecodeHandler(1024, 0, 2, 0, 2));
								}
							}).option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			serverFuture = server.bind(port).sync().channel(); // (7)
			System.out.println("STARTED SERVER WAITING FOR CONNECTION");
			// Wait until the connection is closed.
			serverFuture.closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
