package pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author killpie
 * @date 2018/12/16 9:30
 **/
public class ChannelInboundHandlerA extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerA.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ChannelInboundHandlerA msg:{}"+msg);
        super.channelRead(ctx,msg);
    }
}
