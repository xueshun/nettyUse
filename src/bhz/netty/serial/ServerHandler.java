package bhz.netty.serial;

import java.io.File;
import java.io.FileOutputStream;

import bhz.netty.utils.GzipUtils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter{

	int i = 0;
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel start ...");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		Req req = (Req) msg;
		System.out.println("Server : " +req.getId() + "," + req.getName() + "," + req.getRequestMessage());
		
		byte[] attachment = GzipUtils.ungizp(req.getAttchment());
		String writePath = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "00"+i+".jpg";
		
		FileOutputStream fos = new FileOutputStream(writePath);
		fos.write(attachment);
		fos.close();
		
		Resp  resp = new Resp();
		resp.setId(req.getId());
		resp.setName("resp" + req.getId());
		resp.setResponseMessage("服务器响应内容" + req.getId());
		
		ctx.writeAndFlush(resp);//.addListener(ChannelFutureListener.CLOSE);
		i++;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端请求完毕....");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
