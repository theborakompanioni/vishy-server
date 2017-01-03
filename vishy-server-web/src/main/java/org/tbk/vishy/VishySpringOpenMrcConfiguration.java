package org.tbk.vishy;

import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import com.github.theborakompanioni.openmrc.VishyOpenMrcExtensions;
import com.github.theborakompanioni.openmrc.spring.SpringOpenMrcConfigurationSupport;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.protobuf.ExtensionRegistry;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.vishy.hystrix.HystrixOpenMrcRequestConsumer;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Slf4j
@Configuration
public class VishySpringOpenMrcConfiguration extends SpringOpenMrcConfigurationSupport {

    @Override
    public OpenMrcResponseSupplier openMrcResponseSupplier() {
        return new VishyOpenMrcResponseSupplier();
    }

    @Override
    @Bean
    public ExtensionRegistry extensionRegistry() {
        ExtensionRegistry extensionRegistry = super.extensionRegistry();

        extensionRegistry.add(VishyOpenMrcExtensions.Vishy.vishy);

        return extensionRegistry;
    }

    @Override
    @Bean
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        List<OpenMrcRequestConsumer> consumers = Lists.newArrayList(super.openMrcRequestConsumer());

        log.info("registering {} request consumer(s): {}", consumers.size(), consumers);

        return consumers.stream()
                .map(c -> new HystrixOpenMrcRequestConsumer(defaultHystrixCommandSetter(c.getClass().getSimpleName()), c))
                .collect(Collectors.toList());
    }

    private HystrixCommandGroupKey defaultHystrixCommandGroupKey(String key) {
        requireNonNull(Strings.emptyToNull(key));
        return HystrixCommandGroupKey.Factory.asKey("openmrc-" + key);
    }

    private HystrixCommand.Setter defaultHystrixCommandSetter(String key) {
        requireNonNull(Strings.emptyToNull(key));
        return HystrixCommand.Setter.withGroupKey(defaultHystrixCommandGroupKey(key))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withRequestLogEnabled(false)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(1_000)
                        .withExecutionTimeoutInMilliseconds(1_000));
    }
}
