package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.GroupMessageResponsePacket;

/**
 * @author dingzhaolei
 * @date 2018/12/26 15:55
 **/
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    private static final Logger logger = LoggerFactory.getLogger(GroupMessageResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        logger.info("入参 channelId:{},GroupMessageResponsePacket  :{}",ctx.channel().id(), msg);

        if (msg.isSuccess()){
            logger.info(msg.getMessage());
        }else {
            logger.info(msg.getReason());
        }
    }
}
