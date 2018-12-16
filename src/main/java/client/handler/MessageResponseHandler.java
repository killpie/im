package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.MessageResponsePacket;

/**
 * @author killpie
 * @date 2018/12/16 11:18
 **/
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(MessageResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception{
        logger.info("收到服务端发送的消息:{}",msg);
    }
}
