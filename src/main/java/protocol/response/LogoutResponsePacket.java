package protocol.response;

import protocol.Packet;
import protocol.command.Command;

/**
 * @author dingzhaolei
 * @date 2018/12/24 11:23
 **/
public class LogoutResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
