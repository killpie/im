package server.handler;

import client.handler.LoginResponseHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import util.IDUtil;
import util.SessionUtil;


/**
 * @author killpie
 * @date 2018/12/16 11:42
 **/
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private static final Logger logger = LoggerFactory.getLogger(LoginRequestHandler.class);
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception{
        logger.info("入参 channelId:{},LoginRequestPacket :{}",ctx.channel().id(), msg);


        LoginResponsePacket res = new LoginResponsePacket();
        if (valid(msg)){
            res.setVersion(msg.getVersion());
            res.setUserName(msg.getUserName());
            res.setUserId(IDUtil.randomId());
            res.setSuccess(true);
            res.setReason("x");
            logger.info("登录成功-用户:{}",msg);
            SessionUtil.bindSession(new Session(res.getUserId(), res.getUserName()), ctx.channel());
        }else {
            SessionUtil.unBindSession(ctx.channel());
            res.setSuccess(false);
            logger.info("登录失败-用户:{}",msg);
        }

        ctx.channel().writeAndFlush(res);
    }

    private boolean valid(LoginRequestPacket packet){
        return true;
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        logger.info("channel:{} 停止活动", ctx.channel().id());
        SessionUtil.unBindSession(ctx.channel());
    }
}
