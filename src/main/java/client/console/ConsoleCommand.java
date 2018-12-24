package client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令接口
 * @author dingzhaolei
 * @date 2018/12/24 10:07
 **/
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);

}
