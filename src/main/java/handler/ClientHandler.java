package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Packet;
import protocol.request.LoginRequestPacket;
import protocol.PacketCodeC;
import protocol.response.LoginResponsePacket;
import protocol.response.MessageResponsePacket;
import util.DateUtil;
import util.LoginUtil;

import java.util.UUID;

/**
 * @author killpie
 * @date 2018/12/15 17:51
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        logger.info("用户开始登录:{}", DateUtil.getDateTime());
        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("dzl");
        packet.setPassword("root");

        //编码
       // ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);
        //写数据
        ctx.channel().writeAndFlush(packet);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket)packet;
            if (loginResponsePacket.isSuccess()){
                LoginUtil.maskAsLogin(ctx.channel());
                logger.info("登录成功");
            }
        }

        if (packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
            logger.info("收到服务端发送的消息:{}",messageResponsePacket);
        }
    }
}
