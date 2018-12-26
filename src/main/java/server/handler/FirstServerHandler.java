package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DateUtil;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
@ChannelHandler.Sharable
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(FirstServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf)msg;

        logger.info(DateUtil.getDateTime() +"接受到数据->"+data.toString(Charset.forName("utf-8")));
    }

}
