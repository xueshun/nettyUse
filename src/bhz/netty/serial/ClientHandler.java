package bhz.netty.serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter{

	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client start ...");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			Resp resp = (Resp) msg;
			System.out.println("Client : " + resp.getId() + "," + resp.getName() + "," + resp.getResponseMessage());
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client ��Ϣ�������");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
