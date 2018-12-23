package client;

import client.handler.FirstClientHandler;
import client.handler.LoginResponseHandler;
import client.handler.MessageResponseHandler;
import handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.PacketCodeC;
import protocol.codec.PacketCodec;
import protocol.codec.Spliter;
import protocol.request.LoginRequestPacket;
import protocol.request.MessageRequestPacket;
import util.LoginUtil;
import util.SessionUtil;

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
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketCodec());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
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
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
           while (!Thread.interrupted()){
               logger.info("开始启动,线程中断状态{}，登录状态{}",Thread.interrupted(),LoginUtil.hasLogin(channel));
               if (!SessionUtil.hasLogin(channel)){
                   logger.info("输入用户名登录：");
                   LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                   String userName =scanner.nextLine();
                   loginRequestPacket.setUserName(userName);
                   loginRequestPacket.setPassword("pwd");

                   channel.writeAndFlush(loginRequestPacket);
                   waitForLoginResponse();
               }else {
                   logger.info("请给你的好友发送消息");
                   logger.info("第一行是你要发送的信息，第二行是接收人的id");
                   MessageRequestPacket packet = new MessageRequestPacket();
                   String message = scanner.nextLine();
                   String toUserId = scanner.nextLine();
                   String fromUserId = SessionUtil.getSession(channel).getUserId();
                   packet.setMessage(message);
                   packet.setToUserId(toUserId);
                   packet.setFromUserId(fromUserId);
                   channel.writeAndFlush(packet);
               }
           }
        }).start();
    }

    public static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            logger.error("错误：{}",e);
        }
    }


}