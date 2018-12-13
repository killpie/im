package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf)msg;

        System.out.println(new SimpleDateFormat().format(new Date())+"接受到数据->"+data.toString(Charset.forName("utf-8")));
    }

}
