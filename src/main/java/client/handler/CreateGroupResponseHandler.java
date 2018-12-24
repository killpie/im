package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.CreateGroupResponsePacket;
import server.handler.CreateGroupRequestHandler;

/**
 * @author dingzhaolei
 * @date 2018/12/24 16:08
 **/
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(CreateGroupRequestHandler.class);

    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception{

        logger.info("群创建成功，id 为[{}] ", msg.getGroupId());
        logger.info("群里面有：{}" , msg.getUserNameList() );
    }

}
