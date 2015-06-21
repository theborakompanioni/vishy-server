package org.tbk.vishy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.openmrc.LoggingRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestInterceptor;
import org.tbk.openmrc.web.OpenMrcHttpRequestService;
import org.tbk.openmrc.web.OpenMrcWebConfiguration;
import org.tbk.openmrc.web.OpenMrcWebConfigurationSupport;
import org.tbk.vishy.properties.provider.ExtensionsConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class VishyOpenMrcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ExtensionsConfiguration extensionsConfiguration;

    class SimpleOpenMrcConfiguration extends OpenMrcWebConfigurationSupport {
        public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
            return Arrays.asList(new LoggingRequestConsumer());
        }

        public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
            return Arrays.asList(
                    extensionsConfiguration.browserRequestInterceptor(),
                    extensionsConfiguration.deviceRequestInterceptor(),
                    extensionsConfiguration.localeRequestInterceptor(),
                    extensionsConfiguration.operatingSystemRequestInterceptor(),
                    extensionsConfiguration.referrerRequestInterceptor(),
                    extensionsConfiguration.userAgentRequestInterceptor()
            );
        }
    }

    @Bean
    public OpenMrcWebConfiguration openMrcWebConfiguration() {
        return new SimpleOpenMrcConfiguration();
    }

    @Bean
    public OpenMrcHttpRequestService openMrcHttpRequestService() {
        return openMrcWebConfiguration().httpRequestService();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(jacksonJdk8Module());

        return objectMapper;
    }

    @Bean
    public Jdk8Module jacksonJdk8Module() {
        return new Jdk8Module();
    }

    /*@Bean
    public ServletRegistrationBean vishyOpenMrcServlet(OpenMrcClientList clients) {
        return new ServletRegistrationBean(new VishyOpenMrcServlet(clients), "/openmrc");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int BROWSER_CACHE_CONTROL = 604800;
        registry
                .addResourceHandler("/vishy/scripts/**")
                .addResourceLocations("/vishy/scripts/")
                .setCachePeriod(BROWSER_CACHE_CONTROL);
    }
}
