package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;

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
        messageResponsePacket.setMessage("就不！就不！就不！");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
