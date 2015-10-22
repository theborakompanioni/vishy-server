package org.tbk.vishy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import org.tbk.vishy.web.VishyOpenMrcCtrl;

@Slf4j
@ComponentScan("org.tbk.vishy")
@AutoConfigureAfter({VishyOpenMrcConfiguration.class})
@Configuration
public class VishyServerConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public RequestInterceptorConfiguration extensionsConfiguration() {
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
