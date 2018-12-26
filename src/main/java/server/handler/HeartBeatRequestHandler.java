package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.HeartBeatRequestPacket;
import protocol.response.HeartBeatResponsePacket;

/**
 * @author dingzhaolei
 * @date 2018/12/26 19:25
 **/
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatRequestHandler.class);
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
