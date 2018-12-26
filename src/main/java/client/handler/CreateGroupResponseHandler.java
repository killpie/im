package client.handler;

import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(CreateGroupResponseHandler.class);
    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception{
        logger.info("入参 channelId:{},CreateGroupResponsePacket  :{}",ctx.channel().id(), msg);
        logger.info("群创建成功，id 为[{}] ", msg.getGroupId());
        logger.info("群里面有：{}" , msg.getUserNameList() );
    }

}
