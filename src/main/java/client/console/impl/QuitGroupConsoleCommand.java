package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.QuitGroupRequestPacket;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/25 14:48
 **/
public class QuitGroupConsoleCommand implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(QuitGroupConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("请输入自己要退出群聊的groupId");
        String groupId = scanner.nextLine();

        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
