package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private final static int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException{
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch){
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });


        Channel channel = connect(bootstrap, "127.0.0.1", 8000, 5);
/*        while (true){
            System.out.println(new Date()+": Hello World!");
            channel.writeAndFlush(new Date()+": Hello World!");
            Thread.sleep(2000);
        }*/

    }

    private static Channel connect(final Bootstrap bootstrap, final String host, final int port, final int retry){
        return bootstrap.connect(host,port).addListener(future->{
         if (future.isSuccess()){
             System.out.println("连接成功");
         }else if (retry == 0){
             System.out.println("重试次数已用完");
         }else {
             int order = (MAX_RETRY - retry)+1;
             final int delay = 1 << order;
             System.out.println(new Date()+"第"+order+"次连接重试");
             bootstrap.config().group().schedule(()-> connect(bootstrap,host,port,retry-1),delay, TimeUnit.SECONDS);
         }
        }).channel();
    }
}
