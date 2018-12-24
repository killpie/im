package client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.PacketCodeC;
import protocol.domain.Session;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import util.DateUtil;
import util.LoginUtil;
import util.SessionUtil;

import java.util.UUID;

/**
 * @author killpie
 * @date 2018/12/16 10:51
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private static final Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        logger.info("连接被关闭 发送此消息的channelId:{}", ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception{
        String userId = msg.getUserId();
        String userName = msg.getUserName();

        if (msg.isSuccess()){
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            logger.info("用户:{} 登录成功 userId:{}",userName, userId);
        }else {
            SessionUtil.unBindSession(ctx.channel());
            logger.info("用户:{} 登录成功 userId:{}",userName, userId);
        }
    }
}
