package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.command.Command;
import protocol.request.*;
import protocol.response.*;
import serialize.Serializer;
import serialize.impl.JSONSerializerAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dingzhaolei
 * @date 2018/12/14 18:29
 **/
public class PacketCodeC {
    private static final Logger logger = LoggerFactory.getLogger(PacketCodeC.class);
    public static final int MAGIC_NUMBER = 0x123456;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    //单例模式
    public static final PacketCodeC INSTANCE ;

    static {
        INSTANCE = new PacketCodeC();
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(Command.HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);


        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializerAlgorithm();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }


    public ByteBuf encode(ByteBuf byteBuf, Packet packet){

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

        if (serializer == null){
            logger.error("发现未知序列化算法:{}",serializer);
            return null;
        }

        if (requestType == null){
            logger.error("发现未知数据包格式:{}",requestType);
            return null;
        }

        return serializer.deSerialize(requestType, bytes);

    }
}
