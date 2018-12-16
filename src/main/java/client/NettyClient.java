package client;

import handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.PacketCodeC;
import protocol.request.MessageRequestPacket;
import util.LoginUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private final static int MAX_RETRY = 5;
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    public static void main(String[] args) throws InterruptedException{
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch){
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });


        connect(bootstrap, "127.0.0.1", 8000, 5);


    }

    private static Channel connect(final Bootstrap bootstrap, final String host, final int port, final int retry){
        return bootstrap.connect(host,port).addListener(future->{
         if (future.isSuccess()){
             logger.info("登录成功,准备启动控制台线程。。。");
             Channel channel = ((ChannelFuture)future).channel();
             startConsoleThread(channel);
         }else if (retry == 0){
             logger.info("重试次数已用完");
         }else {
             int order = (MAX_RETRY - retry)+1;
             final int delay = 1 << order;
             logger.info(new Date()+"第"+order+"次连接重试");
             bootstrap.config().group().schedule(()-> connect(bootstrap,host,port,retry-1),delay, TimeUnit.SECONDS);
         }
        }).channel();
    }

    private static void startConsoleThread(Channel channel){

        new Thread(()->{
           while (!Thread.interrupted()){
               logger.info("开始启动,线程中断状态{}，登录状态{}",Thread.interrupted(),LoginUtil.hasLogin(channel));
               if (LoginUtil.hasLogin(channel)){
                   logger.info("输入消息发送至服务端");
                   MessageRequestPacket packet = new MessageRequestPacket();
                   Scanner scanner = new Scanner(System.in);

                   String message = scanner.nextLine();
                   if (StringUtil.isNullOrEmpty(message)){
                       packet.setMessage("不要回答！不要回答！不要回答！");
                   }else {
                       packet.setMessage(message);
                   }
                   ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(),packet);
                   channel.writeAndFlush(byteBuf);
               }
           }
        }).start();
    }
}
