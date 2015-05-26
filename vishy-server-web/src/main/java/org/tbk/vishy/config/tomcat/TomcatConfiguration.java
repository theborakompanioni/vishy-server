package org.tbk.vishy.config.tomcat;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * Created by void on 26.05.15.
 */
@Configuration
public class TomcatConfiguration {
    
    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return servletContainer -> ((TomcatEmbeddedServletContainerFactory) servletContainer).addConnectorCustomizers(
                connector -> {
                    AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
                    httpProtocol.setCompression("on");
                    httpProtocol.setCompressionMinSize(256);
                    String mimeTypes = httpProtocol.getCompressableMimeTypes();
                    String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE;
                    httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);
                }
        );
    }

}
