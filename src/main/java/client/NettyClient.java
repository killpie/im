package client;

import client.console.impl.ConsoleCommandManager;
import client.console.impl.LoginConsoleCommand;
import client.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Packet;
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
    private static final String  HOST = "127.0.0.1";
    private static final int  PORT = 8000;

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
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                    }
                });


        connect(bootstrap, HOST, PORT, MAX_RETRY);


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
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = (LoginConsoleCommand )consoleCommandManager.getConsoleCommandMap().get("login");
        new Thread(()->{
           while (!Thread.interrupted()){
               logger.info("开始启动,线程中断状态{}，登录状态{}",Thread.interrupted(),LoginUtil.hasLogin(channel));
               if (!SessionUtil.hasLogin(channel)){
                   loginConsoleCommand.exec(scanner, channel);
               }else {
                   consoleCommandManager.exec(scanner, channel);
               }
           }
        }).start();
    }




}