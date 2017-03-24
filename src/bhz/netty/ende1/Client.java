package bhz.netty.ende1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * TCP 粘包，拆包为题
 * 		解决方案
 * 	1,分隔符类DelimiterBasedFrameDecoder(自定义分隔符)
 * 	2,FixedLengthFrameDecoder(定长)
 * @author Administrator
 *
 */
public class Client {
	public static void main(String[] args) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		Bootstrap b = new Bootstrap();
		b.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();
		
		cf.channel().writeAndFlush(Unpooled.copiedBuffer("bbbb$_".getBytes()));
		cf.channel().writeAndFlush(Unpooled.copiedBuffer("cccccc$_".getBytes()));
		
		cf.channel().closeFuture().sync();
		workerGroup.shutdownGracefully();
	}
}
