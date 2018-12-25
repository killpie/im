package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;
import protocol.domain.Session;

import java.util.List;

/**
 * @author dingzhaolei
 * @date 2018/12/25 10:20
 **/
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
