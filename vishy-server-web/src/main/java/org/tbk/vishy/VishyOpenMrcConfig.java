package org.tbk.vishy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.openmrc.core.OpenMrcConfig;
import org.tbk.openmrc.core.OpenMrcService;
import org.tbk.openmrc.core.client.OpenMrcClient;
import org.tbk.openmrc.core.client.OpenMrcClientList;
import org.tbk.openmrc.core.properties.evaluator.PropertyExtractionAlgorithm;
import org.tbk.openmrc.core.properties.evaluator.PropertyExtractionAlgorithmFactory;
import org.tbk.openmrc.core.properties.evaluator.PropertyProviderFactoryExtractionAlgorithm;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.openmrc.simple.SimpleOpenMrcService;
import org.tbk.vishy.web.VishyOpenMrcServlet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class VishyOpenMrcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public OpenMrcConfig openMrcConfig(OpenMrcClientList clientList,
                                       PropertyExtractionAlgorithmFactory propertyExtractionAlgorithmFactory,
                                       OpenMrcService openMrcService) {
        return new OpenMrcConfig() {
            @Override
            public OpenMrcClientList clientList() {
                return clientList;
            }

            @Override
            public PropertyExtractionAlgorithmFactory propertyEvaluatorFactory() {
                return propertyExtractionAlgorithmFactory;
            }

            @Override
            public OpenMrcService service() {
                return openMrcService;
            }
        };
    }

    @Bean
    public PropertyExtractionAlgorithmFactory propertyEvaluatorFactory(PropertyExtractionAlgorithm propertyEvaluator) {
        final Optional<PropertyExtractionAlgorithm> optional = Optional.ofNullable(propertyEvaluator);
        return eventName -> optional;
    }

    @Bean
    public PropertyExtractionAlgorithm propertyEvaluator(List<PropertyProviderFactory> propertyProviderFactories) {
        return new PropertyProviderFactoryExtractionAlgorithm(propertyProviderFactories);
    }

    @Bean
    public OpenMrcService openMrcService(PropertyExtractionAlgorithmFactory propertyEvaluatorFactory, OpenMrcClientList clientList) {
        return new SimpleOpenMrcService(propertyEvaluatorFactory, clientList);
    }

    @Bean
    public OpenMrcClient loggingOpenMrcClient() {
        return new OpenMrcClient() {
            @Override
            public String name() {
                return "logging";
            }

            @Override
            public void track(String userId, String event, Map<String, ?> properties) {
                log.info("track {}: {} - {}", userId, event, properties);
            }
        };
    }

    @Bean
    public OpenMrcClientList vishyOpenMrcClientList(List<OpenMrcClient> clients) {
        return new VishyOpenMrcClientList(clients);
    }

    @Bean
    public ServletRegistrationBean vishyOpenMrcServlet(OpenMrcClientList clients) {
        return new ServletRegistrationBean(new VishyOpenMrcServlet(clients), "/openmrc");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int BROWSER_CACHE_CONTROL = 604800;
        registry
                .addResourceHandler("/vishy/scripts/**")
                .addResourceLocations("/vishy/scripts/")
                .setCachePeriod(BROWSER_CACHE_CONTROL);
    }
}
