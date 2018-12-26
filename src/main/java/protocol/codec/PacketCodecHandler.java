package protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import protocol.Packet;
import protocol.PacketCodeC;

import java.util.List;

/**
 * @author killpie
 * @date 2018/12/16 11:23
 **/
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
   public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();


    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), msg);
        out.add(byteBuf);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        out.add(PacketCodeC.INSTANCE.decode(in));
    }

}
