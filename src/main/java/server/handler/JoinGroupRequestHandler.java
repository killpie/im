package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.request.JoinGroupRequestPacket;
import protocol.response.JoinGroupResponsePacket;
import util.SessionUtil;

/**
 * @author dingzhaolei
 * @date 2018/12/25 11:09
 **/
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    private static final Logger logger = LoggerFactory.getLogger(JoinGroupRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        logger.info("入参 channelId:{},JoinGroupRequestPacket :{}",ctx.channel().id(), msg);

        Session session = SessionUtil.getSession(ctx.channel());
        String userId = session.getUserId();
        String userName = session.getUserName();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());

        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setGroupId(msg.getGroupId());

        if (channelGroup != null){
            joinGroupResponsePacket.setSuccess(true);
            joinGroupResponsePacket.setMessage(String.format("用户:【%s】加入群聊 groupId:【%s】,userId:%s",userName,msg.getGroupId(), userId));
            channelGroup.writeAndFlush(joinGroupResponsePacket);

            channelGroup.add(ctx.channel());

            //绑定channelGroup
            SessionUtil.bindChannelGroup(msg.getGroupId(),channelGroup);
            //更新群组中的用户列表
            SessionUtil.addMembers(msg.getGroupId(), session);

            joinGroupResponsePacket.setMessage(String.format("您已加入群聊 groupId:【%s】",msg.getGroupId()));
            ctx.channel().writeAndFlush(joinGroupResponsePacket);
        }else {
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setReason(String.format("未找到groupId是%s 的channelGroup", msg.getGroupId()));

            ctx.channel().writeAndFlush(joinGroupResponsePacket);
        }
    }
}
