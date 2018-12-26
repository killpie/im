package protocol.request;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

/**
 * @author dingzhaolei
 * @date 2018/12/26 14:56
 **/
@Data
public class GroupMessageRequestPacket extends Packet {

    private String fromUserId;
    private String fromUserName;
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
