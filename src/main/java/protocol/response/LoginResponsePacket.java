package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;


@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
