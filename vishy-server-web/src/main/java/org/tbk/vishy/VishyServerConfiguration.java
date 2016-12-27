package org.tbk.vishy;

import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.vishy.client.analytics.AnalyticsScriptLoaderFactory;
import org.tbk.vishy.client.analytics.AnalyticsScriptLoaderFactoryImpl;
import org.tbk.vishy.client.analytics.VishyScriptLoaderCtrl;
import org.tbk.vishy.verticle.HelloVerticle;
import org.tbk.vishy.web.VishyOpenMrcCtrl;

@Slf4j
@ComponentScan(value = {
        "org.tbk.vishy",
        "com.github.theborakompanioni"
})
@AutoConfigureAfter({VishyOpenMrcConfiguration.class})
@Configuration
public class VishyServerConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public HelloVerticle helloVerticle() {
        return new HelloVerticle(8081);
    }

    @Bean
    public RequestInterceptorConfiguration requestInterceptorConfiguration() {
        return new RequestInterceptorConfiguration();
    }

    @Bean
    public VishyOpenMrcCtrl vishyOpenMrcCtrl(OpenMrcHttpRequestService openMrcHttpRequestService) {
        return new VishyOpenMrcCtrl(openMrcHttpRequestService);
    }

    /*@Bean
    public ServletRegistrationBean vishyOpenMrcServlet(OpenMrcClientList clients) {
        return new ServletRegistrationBean(new VishyOpenMrcServlet(clients), "/openmrc");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        /*int BROWSER_CACHE_CONTROL = 604800;
        registry
                .addResourceHandler("/vishy/scripts/**")
                .addResourceLocations("/vishy/scripts/")
                .setCachePeriod(BROWSER_CACHE_CONTROL);*/
    }
}
