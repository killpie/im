package protocol.response;

import protocol.Packet;
import protocol.command.Command;

/**
 * @author dingzhaolei
 * @date 2018/12/26 18:33
 **/
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
