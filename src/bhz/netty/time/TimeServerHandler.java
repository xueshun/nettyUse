package bhz.netty.time;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 和之前的例子不同的是在不接受任何请求时他会发送一个含32位的整数的消息，
 * 并且一旦消息发送就会立即关闭连接。在这个例子中，你会学习到如何构建和发送一个消息，
 * 然后在完成时主动关闭连接。
 * @author Administrator
 *
 */
public class TimeServerHandler extends ChannelHandlerAdapter {


	//channelActive() 方法将会在连接被建立并且准备进行通信是调用。
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//ctx.alloc() 得到一个当前的ByteBufAllocator,然后分配一个新的缓冲
		final ByteBuf time = ctx.alloc().buffer(4);
		//time.writeInt((int)(System.currentTimeMillis()/1000L + 2208988800L));
		time.writeInt(1112222);
		
		final ChannelFuture f = ctx.writeAndFlush(time);
		f.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				assert f == future;
				ctx.close();
			}
		});
		f.addListener(ChannelFutureListener.CLOSE);
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
