package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.LoginRequestPacket;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.request.MessageRequestPacket;
import protocol.response.LoginResponsePacket;
import protocol.response.MessageResponsePacket;
import util.LoginUtil;

/**
 * @author killpie
 * @date 2018/12/15 21:50
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("ctx.name:{}", ctx.name());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket res = new LoginResponsePacket();
            if (valid(loginRequestPacket)){
                res.setSuccess(true);
                res.setReason("x");
                logger.info("登录成功-用户:{}",packet);
            }else {
                res.setSuccess(false);
                logger.info("登录失败-用户:{}",packet);
            }

           // ByteBuf loginRes = PacketCodeC.INSTANCE.encode(ctx.alloc(), res);
            ctx.channel().writeAndFlush(res);
        }

        if (packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket)packet;
            logger.info("客户端发送的消息:{}",messageRequestPacket);
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("就不！就不！就不！");
           // ByteBuf message = PacketCodeC.INSTANCE.encode(ctx.alloc(),messageResponsePacket);
            ctx.channel().writeAndFlush(messageResponsePacket);
        }

    }

    private boolean valid(LoginRequestPacket packet){
        return true;
    }
}
