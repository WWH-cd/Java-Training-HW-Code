package javaTraining.nio.gateway.filters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestHeaderFilter implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        fullHttpRequest.headers().set("requestHeader1", "every dog has its day");
    }
}
