package javaTraining.nio.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import javaTraining.nio.gateway.inbound.HttpInboundHandler;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel channel) {
        // add channel handlers to the channel pipeline
        ChannelPipeline p = channel.pipeline();
        p.addLast(new HttpServerCodec());
//        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpInboundHandler());   // HttpHandler added by me
    }
}
