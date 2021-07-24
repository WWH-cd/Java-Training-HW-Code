package javaTraining.nio.gateway.inbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import javaTraining.nio.gateway.filters.HttpRequestHeaderFilter;
import javaTraining.nio.gateway.filters.HttpRequestFilter;
import javaTraining.nio.gateway.filters.HttpResponseFilter;
import javaTraining.nio.gateway.filters.HttpResponseHeaderFilter;
import javaTraining.nio.gateway.routing.RoutingService;
import javaTraining.nio.gateway.routing.RoutingServiceImpl;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// It handles the traffic coming from the client.
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private HttpRequestFilter requestFilter = new HttpRequestHeaderFilter();
    private HttpResponseFilter responseFilter = new HttpResponseHeaderFilter();
    private OkHttpClient client = new OkHttpClient();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /*
     * ChannelHandlerContext has access to channel handlers on the channel pipeline.
     * msg contains info about the client.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest requestFromClient = (FullHttpRequest) msg;
            processClientRequest(requestFromClient, ctx, requestFilter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /*
     * Request from the client goes through HttpRequestFilter.
     * Add the header acquired from the filter to the request to the backend.
     * Call okHttp to execute the request, and handle the resp in a callback.
     */
    private void processClientRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, HttpRequestFilter filter) {
        filter.filter(fullHttpRequest, ctx);
//        String domain = "http://localhost:";
//        String port = "8801";
//        Request request = new Request.Builder()
//                .url(domain + port + fullHttpRequest.uri())
//                .addHeader("Connection", "Keep-Alive")
//                .addHeader("okRequestHeader1", fullHttpRequest.headers().get("requestHeader1"))
//                .build();

        RoutingService routingService = new RoutingServiceImpl();

        Request request = new Request.Builder()
        .url(routingService.route() + fullHttpRequest.uri())
        .addHeader("Connection", "Keep-Alive")
        .addHeader("okRequestHeader1", fullHttpRequest.headers().get("requestHeader1"))
        .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    backendResponseHandler(fullHttpRequest, ctx, response);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Callback#onResponse: " + e.getMessage());
                }
            }
        });
    }

    /*
     * put the okHttp response into Netty response (server response) for the client
     */
    private void backendResponseHandler(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, Response backendResponse) throws IOException {

        FullHttpResponse fullHttpResponse = null;
        try {
            if (backendResponse == null) {
                System.out.println("response from OkHttpClient is null");
                return;
            }

            String value = backendResponse.body().string();
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());

            responseFilter.filter(fullHttpResponse);
        } catch (Exception e) {
            System.out.println("Parsing error: " + e.getMessage());
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            // write the resp from 8801 to the resp of 8809
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    fullHttpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                    ctx.write(fullHttpResponse);
                }
                ctx.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
