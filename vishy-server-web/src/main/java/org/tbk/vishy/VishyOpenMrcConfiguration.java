package org.tbk.vishy;

import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.VishyOpenMrcExtensions;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.spring.SpringOpenMrcConfigurationSupport;
import com.github.theborakompanioni.openmrc.mapper.StandardOpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.spring.web.OpenMrcHttpRequestService;
import com.google.common.collect.Lists;
import com.google.protobuf.ExtensionRegistry;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tbk.vishy.hystrix.HystrixOpenMrcRequestInterceptor;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class VishyOpenMrcConfiguration extends SpringOpenMrcConfigurationSupport {

    @Bean
    @ConditionalOnMissingBean(StandardOpenMrcJsonMapper.class)
    public StandardOpenMrcJsonMapper openMrcJsonMapper() {
        return new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
    }

    @Override
    @Bean
    public OpenMrcHttpRequestService httpRequestService() {
        /*return new HystrixVishyOpenMrcHttpRequestService(httpRequestMapper(),
                defaultHystrixCommandSetter(),
                openMrcRequestConsumer());*/
        return new OpenMrcHttpRequestService(httpRequestMapper(), openMrcRequestConsumer());
    }


    @Override
    @Bean
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        List<OpenMrcRequestConsumer> consumers = Lists.newArrayList(super.openMrcRequestConsumer());

        if (consumers.isEmpty()) {
            log.warn("No request consumer found. Registering standard consumers.");
            consumers.addAll(super.openMrcRequestConsumer());
        }

        log.info("registering {} request consumer(s): {}", consumers.size(), consumers);

        return consumers.stream()
                .map(c -> new HystrixOpenMrcRequestInterceptor(defaultHystrixCommandSetter(), c))
                .collect(Collectors.toList());
    }

    @Bean
    public HystrixCommandGroupKey defaultHystrixCommandGroupKey() {
        return HystrixCommandGroupKey.Factory.asKey("ProcessOpenMrcRequest");
    }

    @Bean
    public HystrixCommand.Setter defaultHystrixCommandSetter() {
        return HystrixCommand.Setter.withGroupKey(defaultHystrixCommandGroupKey())
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withRequestLogEnabled(false)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(1_000)
                        .withExecutionTimeoutInMilliseconds(5_000));
    }

    @Override
    @Bean
    @Primary
    public OpenMrcHttpRequestMapper httpRequestMapper() {
        return super.httpRequestMapper();
    }

    @Override
    @Bean
    public ExtensionRegistry extensionRegistry() {
        ExtensionRegistry extensionRegistry = super.extensionRegistry();

        extensionRegistry.add(VishyOpenMrcExtensions.Vishy.vishy);

        return extensionRegistry;
    }
}
