package bhz.netty.runtime;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;


/**
 * Best Do It
 */
public class Client {
	
	private static class SingletonHolder {
		static final Client instance = new Client();
	}
	
	public static Client getInstance(){
		return SingletonHolder.instance;
	}
	
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf ;
	
	private Client(){
			group = new NioEventLoopGroup();
			b = new Bootstrap();
			b.group(group)
			 .channel(NioSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			 .handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecoder());
						sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
						//��ʱhandler��������������ͻ�����ָ��ʱ������û���κν���ͨ�ţ����ر���Ӧ��ͨ������ҪΪ��С�������Դռ�ã�
						sc.pipeline().addLast(new ReadTimeoutHandler(5)); 
						sc.pipeline().addLast(new ClientHandler());
					}
		    });
	}
	
	public void connect(){
		try {
			this.cf = b.connect("127.0.0.1", 8765).sync();
			System.out.println("Զ�̷������Ѿ�����, ���Խ������ݽ���..");				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChannelFuture getChannelFuture(){
		
		if(this.cf == null){
			this.connect();
		}
		if(!this.cf.channel().isActive()){
			this.connect();
		}
		
		return this.cf;
	}
	
	public static void main(String[] args) throws Exception{
		final Client c = Client.getInstance();
		//c.connect();
		
		ChannelFuture cf = c.getChannelFuture();
		for(int i = 1; i <= 3; i++ ){
			Request request = new Request();
			request.setId("" + i);
			request.setName("pro" + i);
			request.setRequestMessage("������Ϣ" + i);
			cf.channel().writeAndFlush(request);
			TimeUnit.SECONDS.sleep(4);
		}

		cf.channel().closeFuture().sync();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("�������߳�...");
					ChannelFuture cf = c.getChannelFuture();
					System.out.println(cf.channel().isActive());
					System.out.println(cf.channel().isOpen());
					
					//�ٴη�������
					Request request = new Request();
					request.setId("" + 4);
					request.setName("pro" + 4);
					request.setRequestMessage("������Ϣ" + 4);
					cf.channel().writeAndFlush(request);					
					cf.channel().closeFuture().sync();
					System.out.println("���߳̽���.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		System.out.println("�Ͽ�����,���߳̽���..");
		
	}
	
	
	
}
