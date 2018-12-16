package protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocol.Packet;
import protocol.PacketCodeC;

import java.util.List;

/**
 * @author killpie
 * @date 2018/12/16 11:23
 **/
public class PacketCodec extends ByteToMessageCodec<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception{
        PacketCodeC.INSTANCE.encode(out, msg);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        out.add(PacketCodeC.INSTANCE.decode(in));
    }

}
