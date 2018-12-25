package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.ListGroupMembersRequestPacket;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/25 16:04
 **/
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    private static final Logger logger = LoggerFactory.getLogger(ListGroupMembersConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("请输入你要查看的群聊的groupId");

        String groupId = scanner.nextLine();
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();
        listGroupMembersRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
