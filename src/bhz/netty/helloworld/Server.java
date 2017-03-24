package bhz.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	public static void main(String[] args) throws Exception {
		//1.创建两个线程
		// 一个是用于处理服务器端接收客户端连接的
		// 一个是用于网络通信的(网络读写)
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//2.创建辅助工具类，用于服务器通道的一系列配置
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup,workerGroup)       			//绑定两个线程组
			.channel(NioServerSocketChannel.class)		//指定Nio的模式
			.option(ChannelOption.SO_BACKLOG, 1024)		//设置tcp缓冲区
			.option(ChannelOption.SO_SNDBUF, 32*1024)	//设置发送缓冲大小
			.option(ChannelOption.SO_RCVBUF, 32*1024)   //设置接收缓冲大小
			.option(ChannelOption.SO_KEEPALIVE, true)  	//保持连接
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//3. 在这里配置具体数据接收方法的处理
					ch.pipeline().addLast(new ServerHandler());
				}
			});
		
		//4.绑定
		ChannelFuture cf = b.bind(8765).sync();
		
		//5.等待关闭
		cf.channel().closeFuture().sync();
		
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		
	}
}
