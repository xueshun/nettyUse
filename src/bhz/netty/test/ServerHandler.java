package bhz.netty.test;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler  extends ChannelHandlerAdapter {

	Scanner sc = new Scanner(System.in);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
		
			//do something msg
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("Server: " + request);
			//写给客户端
			String response = "服务器："+sc.nextLine();
			if(response != null){
				ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
			}
		
			//.addListener(ChannelFutureListener.CLOSE);	

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
