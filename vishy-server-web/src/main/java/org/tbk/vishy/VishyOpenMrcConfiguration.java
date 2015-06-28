package org.tbk.vishy;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestInterceptor;
import org.tbk.openmrc.mapper.OpenMrcHttpRequestMapper;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by void on 28.06.15.
 */
@Slf4j
@Configuration
public class VishyOpenMrcConfiguration extends SpringOpenMrcConfigurationSupport {

    @Autowired(required = false)
    private List<OpenMrcRequestConsumer> requestConsumers = Collections.emptyList();

    @Autowired(required = false)
    private List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptors = Collections.emptyList();

    @Bean
    @ConditionalOnMissingClass(StandardOpenMrcJsonMapper.class)
    public StandardOpenMrcJsonMapper openMrcJsonMapper() {
        StandardOpenMrcJsonMapper standardOpenMrcJsonMapper = new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
        return standardOpenMrcJsonMapper;
    }

    @Override
    @Bean
    @Primary
    public OpenMrcHttpRequestMapper httpRequestMapper() {
        return super.httpRequestMapper();
    }

    @Override
    @Bean
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        List<OpenMrcRequestConsumer> consumers = Lists.newArrayList(requestConsumers);

        if (consumers.isEmpty()) {
            log.warn("No request consumer found. Registering standard consumers.");
            consumers.addAll(super.openMrcRequestConsumer());
        }

        log.info("registering {} request consumer(s): {}", consumers.size(), consumers);
        return consumers;
    }

    @Override
    @Bean
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
        List<OpenMrcRequestInterceptor<HttpServletRequest>> interceptors = Lists.newArrayList(this.requestInterceptors);

        if (interceptors.isEmpty()) {
            log.warn("No request consumer found. Registering standard consumers.");
            interceptors.addAll(super.httpRequestInterceptor());
        }

        log.info("registering {} request consumer(s): {}", interceptors.size(), interceptors);
        return interceptors;
    }
}
