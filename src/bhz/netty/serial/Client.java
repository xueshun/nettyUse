package bhz.netty.serial;



import java.io.File;
import java.io.FileInputStream;

import bhz.netty.utils.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	public static void main(String[] args) throws Throwable {
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});
		
		ChannelFuture cf = b.connect("127.0.0.1",8765).sync();
		
		
		for (int i = 0; i < 5; i++) {
			Req req = new Req();
			req.setId("" + i);
			req.setName("request" + i);
			req.setRequestMessage("数据信息：" + i);
			
			String readPath = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "006.jpg";
			
			File file = new File(readPath);
			
			FileInputStream in = new FileInputStream(file);
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();
			req.setAttchment(GzipUtils.gzip(data));
			cf.channel().writeAndFlush(req);
		}
		cf.channel().closeFuture().sync();
		
		workerGroup.shutdownGracefully();
	}
}
