package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.TimeUnit;


/**
 * @author dingzhaolei
 * @date 2018/12/26 18:35
 **/
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;
    private static final Logger logger = LoggerFactory.getLogger(IMIdleStateHandler.class);

    public IMIdleStateHandler(){
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        logger.info("{}秒没有接受到客户端channelId:{}消息，将强制关闭连接", READER_IDLE_TIME, ctx.channel().id());
        ctx.channel().close();
    }


}
