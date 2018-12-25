package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.QuitGroupResponsePacket;


/**
 * @author dingzhaolei
 * @date 2018/12/25 15:35
 **/
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(QuitGroupResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        logger.info("入参 channelId:{},QuitGroupResponsePacket :{}",ctx.channel().id(), msg);
        if (msg.isSuccess()){
            logger.info(msg.getMessage());
        }else {
            logger.info(msg.getReason());
        }
    }
}
