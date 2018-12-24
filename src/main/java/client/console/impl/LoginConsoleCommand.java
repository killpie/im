package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.LoginRequestPacket;

import java.util.Scanner;

/**
 * @author dingzhaolei
 * @date 2018/12/24 12:56
 **/
public class LoginConsoleCommand implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(LoginConsoleCommand.class);

    @Override
    public void exec(Scanner scanner, Channel channel) {
        logger.info("输入用户名登录：");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String userName =scanner.nextLine();
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("pwd");

        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }


    public static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            logger.error("错误：{}",e);
        }
    }
}
