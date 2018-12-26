package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

/**
 * @author dingzhaolei
 * @date 2018/12/26 19:05
 **/
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatTimerHandler.class);
    private static final int HEART_BEAT_REVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(()->{
            if (ctx.channel().isActive()){
                ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_REVAL, TimeUnit.SECONDS);
    }

}
