package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.request.GroupMessageRequestPacket;
import protocol.request.MessageRequestPacket;
import util.SessionUtil;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/25 19:31
 **/
public class SendToGroupConsoleCommamd implements ConsoleCommand {
    private static final Logger logger = LoggerFactory.getLogger(SendToGroupConsoleCommamd.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("请输入您要发信息的群聊的groupId");

        String groupId = scanner.nextLine().trim();

        Session session = SessionUtil.getSession(channel);

        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        groupMessageRequestPacket.setFromUserId(session.getUserId());
        groupMessageRequestPacket.setFromUserName(session.getUserName());
        groupMessageRequestPacket.setGroupId(groupId);

        logger.info("请输入您要发送的信息");

        String message = scanner.nextLine();
        groupMessageRequestPacket.setMessage(message);

        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
