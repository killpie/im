package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.MessageRequestPacket;
import util.SessionUtil;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/24 14:21
 **/
public class SendToUserConsoleCommand  implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(SendToUserConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("请给你的好友发送消息");
        logger.info("第一行是你要发送的信息，第二行是接收人的id");
        MessageRequestPacket packet = new MessageRequestPacket();
        String message = scanner.nextLine();
        String toUserId = scanner.nextLine();
        String fromUserId = SessionUtil.getSession(channel).getUserId();
        packet.setMessage(message);
        packet.setToUserId(toUserId);
        packet.setFromUserId(fromUserId);
        channel.writeAndFlush(packet);
    }
}
