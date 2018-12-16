package client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.request.MessageRequestPacket;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {

        for (int i = 0; i < 100; i++) {
            //1. 获取数据
            MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
            messageRequestPacket.setMessage("你好，年华->"+i);
            channelHandlerContext.channel().writeAndFlush(messageRequestPacket);
        }

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf = ctx.alloc().buffer();

        byte[] data = "你好，年华".getBytes(Charset.forName("utf-8"));

        byteBuf.writeBytes(data);

        return byteBuf;
    }
}
