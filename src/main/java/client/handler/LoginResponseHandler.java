package client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.PacketCodeC;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import util.DateUtil;
import util.LoginUtil;

import java.util.UUID;

/**
 * @author killpie
 * @date 2018/12/16 10:51
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);

    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        logger.info("用户开始登录:{}", DateUtil.getDateTime());
        //创建登录对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("dzl");
        packet.setPassword("root");

        //写数据
        ctx.channel().writeAndFlush(packet);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception{
        if (msg.isSuccess()){
            LoginUtil.maskAsLogin(ctx.channel());
            logger.info("登录成功");
        }else {
            logger.info("登录失败");
        }
    }
}
