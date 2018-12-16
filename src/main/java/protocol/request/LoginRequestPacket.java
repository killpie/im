package protocol.request;

import lombok.Data;
import protocol.command.Command;
import protocol.Packet;

/**
 * @author dingzhaolei
 * @date 2018/12/14 13:26
 **/
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
