package javaTraining.nio;

import javaTraining.nio.gateway.inbound.HttpInboundServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NioApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(NioApplication.class, args);
		String proxyPort = ctx.getEnvironment().getProperty("proxy.port");
		if (proxyPort == null) {
			proxyPort = "8888";
		}
//		System.out.println(proxyPort);

		System.setProperty("app.routers", "http://localhost:8801,http://localhost:8802");

		HttpInboundServer inboundServer = new HttpInboundServer(Integer.parseInt(proxyPort));
		try {
			inboundServer.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
