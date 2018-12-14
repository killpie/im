package transferProtocol;

import com.alibaba.fastjson.serializer.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingzhaolei
 * @date 2018/12/14 18:29
 **/
public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x123456;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializerAlgorithm();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //开始编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        //跳过魔数
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = packetTypeMap.get(command);

        Serializer serializer = serializerMap.get(serializerAlgorithm);

        if (serializer != null && requestType != null){
            return serializer.deSerialize(requestType, bytes);
        }

        return null;
    }
}
