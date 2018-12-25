package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.JoinGroupResponsePacket;

/**
 * @author dingzhaolei
 * @date 2018/12/25 13:13
 **/
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(JoinGroupResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        logger.info("入参 channelId:{},JoinGroupResponsePacket:{}",ctx.channel().id(), msg);
        if (msg.isSuccess()){
            logger.info(msg.getMessage());
        }else {
            logger.info(msg.getReason());
        }
    }
}
