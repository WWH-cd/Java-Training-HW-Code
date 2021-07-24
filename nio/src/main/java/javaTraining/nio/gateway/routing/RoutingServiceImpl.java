package javaTraining.nio.gateway.routing;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RoutingServiceImpl implements RoutingService{
    String routing;

    private String formatURL(String url) {
        return url.endsWith("/") ? url.substring(0, url.length()-1) : url;
    }

    @Override
    public String route() {
        routing = System.getProperty("app.routers", "http://localhost:8802");
        List<String> urls = Arrays.asList(routing.split(","));
        int urlCounts = urls.size();
        Random random = new Random(System.currentTimeMillis());

        urls = urls.stream().map(this::formatURL).collect(Collectors.toList());
        return urls.get(random.nextInt(urlCounts));
    }
}
