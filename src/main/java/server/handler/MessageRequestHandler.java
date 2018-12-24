package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;
import util.SessionUtil;

/**
 * @author killpie
 * @date 2018/12/16 11:42
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    private static final Logger logger = LoggerFactory.getLogger(MessageRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception{
        logger.info("客户端发送的消息:{}",msg);
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        String fromUserId = msg.getFromUserId();
        messageResponsePacket.setMessage(String.format("谢谢 userId:%s 给我发送的message:%s", fromUserId, msg.getMessage()));

        String toUserId = msg.getToUserId();
        Channel channel = SessionUtil.getChannel(toUserId);

        if (channel != null && SessionUtil.hasLogin(channel)){
            channel.writeAndFlush(messageResponsePacket);
        }else {
            logger.info("userId:{} 不在线", toUserId);
        }
    }
}
