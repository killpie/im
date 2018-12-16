package pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author killpie
 * @date 2018/12/16 9:36
 **/
public class ChannelOutboundHandlerA extends ChannelOutboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelOutboundHandlerA.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("ChannelOutboundHandlerA msg:{}",msg);
        super.write(ctx,msg,promise);
    }

}
