package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/24 13:04
 **/
public class LogoutConsoleCommand implements ConsoleCommand {
    private static final Logger logger = LoggerFactory.getLogger(LogoutConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {

    }
}
