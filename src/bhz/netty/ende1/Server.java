package bhz.netty.ende1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
	public static void main(String[] args) throws Exception {
		//1.创建两个线程 一个负责接收客户端，一个负责进行数据传输
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		
		//2.创建服务器辅助类
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup,cGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.option(ChannelOption.SO_SNDBUF, 32*1024)
			.option(ChannelOption.SO_RCVBUF, 32*1024)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//3.设置特殊分隔符
					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
					//4.设置字符串形式的解码
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new ServerHandler());
				}
			});
		
		//5.绑定连接
		ChannelFuture cf = b.bind(8765).sync();
		
		//6.等待服务器监听端口关闭
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
		
	}
}
