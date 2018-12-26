package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

/**
 * @author dingzhaolei
 * @date 2018/12/26 14:59
 **/
@Data
public class GroupMessageResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private String reason;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
