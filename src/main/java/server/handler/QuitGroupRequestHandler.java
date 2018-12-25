package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.request.QuitGroupRequestPacket;
import protocol.response.QuitGroupResponsePacket;
import util.SessionUtil;


/**
 * @author dingzhaolei
 * @date 2018/12/25 14:53
 **/
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    private static final Logger logger = LoggerFactory.getLogger(QuitGroupRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        logger.info("入参 channelId:{},QuitGroupRequestPacket :{}",ctx.channel().id(), msg);

        String groupId = msg.getGroupId();
        Session session = SessionUtil.getSession(ctx.channel());

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(groupId);
        quitGroupResponsePacket.setSuccess(true);

        SessionUtil.removeMembers(groupId,session);
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        //给退出的成员发消息
        quitGroupResponsePacket.setMessage(String.format("您已退出群聊groupId:%s", groupId));
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
        //给群里的其他人发消息
        quitGroupResponsePacket.setMessage(String.format("成员userId:%s退出群聊groupId:%s", session.getUserId(), groupId));
        channelGroup.writeAndFlush(quitGroupResponsePacket);
        //更新群聊分组
        SessionUtil.bindChannelGroup(groupId, channelGroup);


        logger.info("出参 quitGroupResponsePacket:{}", quitGroupResponsePacket);

    }
}
