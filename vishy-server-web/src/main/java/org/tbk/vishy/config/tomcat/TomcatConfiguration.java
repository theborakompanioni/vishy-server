package org.tbk.vishy.config.tomcat;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Configuration
public class TomcatConfiguration {
    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer)
                .addConnectorCustomizers(connector -> {
                    connector.setAsyncTimeout(30_000);
                    connector.setXpoweredBy(false);

                    AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(256);

                    String mimeTypes = Arrays.stream(httpProtocol.getCompressableMimeTypes()).collect(joining(","));
                    String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE;
                    httpProtocol.setCompressableMimeType(mimeTypesWithJson);
                });
    }
}
