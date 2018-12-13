package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println(new SimpleDateFormat().format(new Date())+"客户端写出数据");
        //1. 获取数据
        ByteBuf byteBuf = getByteBuf(channelHandlerContext);

        channelHandlerContext.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf = ctx.alloc().buffer();

        byte[] data = "你好，年华".getBytes(Charset.forName("utf-8"));

        byteBuf.writeBytes(data);

        return byteBuf;
    }
}
