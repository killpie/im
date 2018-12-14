package transferProtocol;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.junit.Test;

/**
 * @author dingzhaolei
 * @date 2018/12/14 19:40
 **/
public class PacketCodeCTest {
    @Test
    public void encode(){
        PacketCodeC packetCodeC = new PacketCodeC();
        Packet packet = new LoginRequestPacket();
        ((LoginRequestPacket) packet).setPassword("123456");
        ((LoginRequestPacket) packet).setUserId("qaz123456");
        ((LoginRequestPacket) packet).setUserName("丁赵雷");
        ByteBuf byteBuf = packetCodeC.encode(packet);

        Packet dePacket = packetCodeC.decode(byteBuf);

        System.out.println(dePacket);
    }

    @Test
    public void decode(){

    }
}
