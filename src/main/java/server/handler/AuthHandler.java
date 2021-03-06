package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.domain.Session;
import util.SessionUtil;

/**
 * @author dingzhaolei
 * @date 2018/12/24 9:39
 **/
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("用户已登录该handler被移除");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("入参 channelId:{},Object :{}",ctx.channel().id(), msg);
        if (!SessionUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


}
