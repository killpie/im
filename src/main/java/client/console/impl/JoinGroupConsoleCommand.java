package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.JoinGroupRequestPacket;
import util.SessionUtil;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/24 18:37
 **/
public class JoinGroupConsoleCommand implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(JoinGroupConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("请输入你要加入群聊的groupId");
        String groupId = scanner.nextLine();

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        joinGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
