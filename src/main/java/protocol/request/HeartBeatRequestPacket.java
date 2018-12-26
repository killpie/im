package protocol.request;

import protocol.Packet;
import protocol.command.Command;

/**
 * @author dingzhaolei
 * @date 2018/12/26 18:33
 **/
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
