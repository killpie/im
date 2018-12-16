package server;

import handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.*;

public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch){


                        // inBound，处理读数据的逻辑链
                        ch.pipeline().addLast(new ChannelInboundHandlerA());
                        ch.pipeline().addLast(new ChannelInboundHandlerB());
                        ch.pipeline().addLast(new ChannelInboundHandlerC());

                        ch.pipeline().addLast(new ServerHandler());

                        // outBound，处理写数据的逻辑链
                        ch.pipeline().addLast(new ChannelOutboundHandlerA());
                        ch.pipeline().addLast(new ChannelOutboundHandlerB());
                        ch.pipeline().addLast(new ChannelOutboundHandlerC());
                    }
                })
                .bind(8000)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()){
                            logger.info("服务器启动成功");
                        }else {
                            logger.info("服务器启动失败");
                        }
                    }
                });
    }
}
