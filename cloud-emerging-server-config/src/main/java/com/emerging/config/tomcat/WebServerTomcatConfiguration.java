package com.emerging.config.tomcat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;


@SpringBootConfiguration
public class WebServerTomcatConfiguration {
	
	
	@Value("${server.port}")
	Integer httpPort;

	@Bean
	public ServletWebServerFactory  servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	
		tomcat.addConnectorCustomizers(connector ->{
				connector.setPort(httpPort);
				connector.setAttribute("connectionTimeout", 10000);
	            connector.setAttribute("acceptorThreadCount", 4);
	            connector.setAttribute("minSpareThreads", 50);
	            connector.setAttribute("maxSpareThreads", 50);
	            connector.setAttribute("maxThreads", 1000);
	            connector.setAttribute("maxConnections", 10000);
	            // 轻量架构高并发，短链接，长链接用NIO2
	            connector.setAttribute("protocol", "org.apache.coyote.http11.Http11NioProtocol");
//	            connector.setAttribute("redirectPort", 443);
	            connector.setAttribute("compression", "on");
		});
		return tomcat;
	}

}