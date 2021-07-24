package javaTraining.nio.gateway.filters;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpResponseHeaderFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("ww", "nio2-resp");
    }
}
