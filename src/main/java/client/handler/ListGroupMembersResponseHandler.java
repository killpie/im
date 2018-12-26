package client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.ListGroupMembersResponsePacket;

/**
 * @author dingzhaolei
 * @date 2018/12/25 16:21
 **/
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    private static final Logger logger = LoggerFactory.getLogger(ListGroupMembersResponseHandler.class);
    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        logger.info("入参 channelId:{},ListGroupMembersResponsePacket:{}",ctx.channel().id(), msg);
        if (msg.isSuccess()){
            logger.info("groupId:{},membersList:{}",msg.getGroupId(), msg.getSessionList());
        }else {
            logger.info(msg.getReason());
        }
    }
}
