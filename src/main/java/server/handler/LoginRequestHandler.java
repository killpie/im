package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.PacketCodeC;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;

/**
 * @author killpie
 * @date 2018/12/16 11:42
 **/
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final Logger logger = LoggerFactory.getLogger(LoginRequestHandler.class);

    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception{
        LoginResponsePacket res = new LoginResponsePacket();
        if (valid(msg)){
            res.setSuccess(true);
            res.setReason("x");
            logger.info("登录成功-用户:{}",msg);
        }else {
            res.setSuccess(false);
            logger.info("登录失败-用户:{}",msg);
        }

        ctx.channel().writeAndFlush(res);
    }

    private boolean valid(LoginRequestPacket packet){
        return true;
    }
}
