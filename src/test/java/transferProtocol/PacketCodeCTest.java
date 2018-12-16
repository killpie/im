package transferProtocol;

import io.netty.buffer.ByteBuf;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.request.LoginRequestPacket;
import util.DateUtil;

/**
 * @author dingzhaolei
 * @date 2018/12/14 19:40
 **/
public class PacketCodeCTest {
    private static final Logger logger = LoggerFactory.getLogger(PacketCodeCTest.class);
    @Test
    public void encode(){
        logger.info("date:{}", DateUtil.getDateTime());
        PacketCodeC packetCodeC = new PacketCodeC();
        Packet packet = new LoginRequestPacket();
        ((LoginRequestPacket) packet).setPassword("123456");
        ((LoginRequestPacket) packet).setUserId("qaz123456");
        ((LoginRequestPacket) packet).setUserName("丁赵雷");
        ByteBuf byteBuf = packetCodeC.encode(packet);

        Packet dePacket = packetCodeC.decode(byteBuf);

        logger.info("data:{}",dePacket);
    }

    @Test
    public void decode(){

    }
}
