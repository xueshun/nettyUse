package bhz.netty.httpfile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	private static final String DEFAULT_URL = "/sources/";

	private void run(final int port, final String url) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						
						//����http�Ľ�����
						ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
						
						//����ObjectAggregator�����������������Ѷ����Ϣת��Ϊ��һ��FullHttpRequset����
						//FullHttpResponse
						ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
						
						//����http������
						ch.pipeline().addLast("http-enconder",new HttpResponseEncoder());
						
						//����chunked 
						//��Ҫ������֧���첽��������(���ļ�����)������ռ���ڴ棬��ֹjava�ڴ����
						ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
						
						//�����Զ��崦���ļ���������ҵ���߼�
						ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
						
					}
				});
			
			ChannelFuture future = b.bind("192.168.1.210",port).sync();
			
			System.out.println("HTTP�ļ�Ŀ¼��������������ַ�� �� " + "192.168.1.210:" +port + url);
			
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8765;
		String url = DEFAULT_URL;
		new HttpFileServer().run(port, url);
	}
}
