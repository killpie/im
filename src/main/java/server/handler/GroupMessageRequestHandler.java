package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.GroupMessageRequestPacket;
import protocol.response.GroupMessageResponsePacket;
import util.DateUtil;
import util.SessionUtil;

/**
 * @author dingzhaolei
 * @date 2018/12/26 15:21
 **/
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    private static final Logger logger = LoggerFactory.getLogger(GroupMessageRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        logger.info("入参 channelId:{},GroupMessageRequestPacket :{}",ctx.channel().id(), msg);
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setGroupId(groupId);


        if (channelGroup != null){
            String message = String.format("\n[%s:%s] %s\n%s",groupId, msg.getFromUserName(), DateUtil.getDateTime(), msg.getMessage());
            groupMessageResponsePacket.setMessage(message);
            groupMessageResponsePacket.setSuccess(true);
            channelGroup.writeAndFlush(groupMessageResponsePacket);
        }else {
            String reason = String.format("不存在groupId为%s的群组",groupId);
            groupMessageResponsePacket.setSuccess(false);
            groupMessageResponsePacket.setReason(reason);
            ctx.channel().writeAndFlush(groupMessageResponsePacket);
        }
    }
}
