package protocol.request;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

/**
 * @author killpie
 * @date 2018/12/15 23:01
 **/
@Data
public class MessageRequestPacket extends Packet {
    private String message;
    private String toUserId;
    private String fromUserId;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
