package pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author killpie
 * @date 2018/12/16 9:35
 **/
public class ChannelInboundHandlerC extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerC.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("ChannelInboundHandlerC msg:{}"+msg);
        super.channelRead(ctx,msg);
    }
}
