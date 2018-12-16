package pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author killpie
 * @date 2018/12/16 9:34
 **/
public class ChannelInboundHandlerB extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerB.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ChannelInboundHandlerB msg:{}"+msg);
        super.channelRead(ctx,msg);
    }
}
