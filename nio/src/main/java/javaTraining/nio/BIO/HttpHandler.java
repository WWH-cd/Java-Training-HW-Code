package javaTraining.nio.BIO;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /*
         * ctx has access to channel handlers on the pipeline.
         * msg contains info about the client.
         */
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
//            String uri = fullHttpRequest.uri();
            requestHandler(fullHttpRequest, ctx);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void requestHandler(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String domain = "http://localhost:";
        String port = "8801";
        Request request = new Request.Builder()
                .url(domain + port + fullHttpRequest.uri()).build();
        Response response = null;
        try {
            // call the backend server 8801 and get the response
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FullHttpResponse fullHttpResponse = null;
        try {
            if (response == null) {
                System.out.println("response from OkHttpClient is null");
                return;
            }

            // process the resp from 8801
            String value = response.body().string();

            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
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
