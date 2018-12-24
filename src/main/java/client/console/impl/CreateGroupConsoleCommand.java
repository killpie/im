package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/24 13:06
 **/
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(CreateGroupConsoleCommand.class);

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        logger.info("【拉人群聊】输入userId列表，以英文逗号(,)隔开");
        String  userIds = scanner.nextLine();

        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
