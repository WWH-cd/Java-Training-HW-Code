package javaTraining.nio.gateway.filters;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {
    void filter(FullHttpResponse response);
}
