package client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import protocol.response.LoginResponsePacket;
import util.SessionUtil;



/**
 * @author killpie
 * @date 2018/12/16 10:51
 **/
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    private static final Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);
    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        logger.info("连接被关闭 发送此消息的channelId:{}", ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception{

        logger.info("入参 channelId:{},LoginResponsePacket :{}",ctx.channel().id(), msg);

        String userId = msg.getUserId();
        String userName = msg.getUserName();

        if (msg.isSuccess()){
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            logger.info("用户:{} 登录成功 userId:{}",userName, userId);
        }else {
            SessionUtil.unBindSession(ctx.channel());
            logger.info("用户:{} 登录失败 userId:{}",userName, userId);
        }
    }
}
