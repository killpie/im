package client.console.impl;

import client.console.ConsoleCommand;
import io.netty.channel.Channel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.command.Command;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dingzhaolei
 * @date 2018/12/24 10:16
 **/
@Data
public class ConsoleCommandManager implements ConsoleCommand {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleCommandManager.class);
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager(){
        consoleCommandMap = new ConcurrentHashMap<>();
        consoleCommandMap.put("login",new LoginConsoleCommand());
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

        logger.info("现有指令：login，logout，sendToUser，createGroup");
        //获取指令
        String command = scanner.nextLine();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null){
            consoleCommand.exec(scanner,channel);
        }else {
            logger.info("无法识别该指令:{},请重新输入",channel);
        }
    }
}
