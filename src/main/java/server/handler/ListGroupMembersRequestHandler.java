package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.request.ListGroupMembersRequestPacket;
import protocol.response.ListGroupMembersResponsePacket;
import util.SessionUtil;

import java.util.List;

/**
 * @author dingzhaolei
 * @date 2018/12/25 16:08
 **/
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    private static final Logger logger = LoggerFactory.getLogger(ListGroupMembersRequestHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        logger.info("入参 channelId:{},ListGroupMembersRequestPacket :{}",ctx.channel().id(), msg);

        String groupId = msg.getGroupId();
        List<Session> list = SessionUtil.listMembers(groupId);
        Session session = SessionUtil.getSession(ctx.channel());

        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        if (list == null || !list.contains(session)){
            listGroupMembersResponsePacket.setSuccess(false);
            listGroupMembersResponsePacket.setReason(String.format("您没有权限查看该群组的成员列表groupId:%s",groupId));
        }else {
            listGroupMembersResponsePacket.setSessionList(list);
            listGroupMembersResponsePacket.setGroupId(groupId);
            listGroupMembersResponsePacket.setSuccess(true);
        }

        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
