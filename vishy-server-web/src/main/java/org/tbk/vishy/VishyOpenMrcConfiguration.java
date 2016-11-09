package org.tbk.vishy;

import com.github.theborakompanioni.openmrc.SpringOpenMrcConfigurationSupport;
import com.github.theborakompanioni.openmrc.VishyOpenMrcExtensions;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.mapper.StandardOpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import com.google.protobuf.ExtensionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tbk.vishy.hystrix.HystrixVishyOpenMrcHttpRequestService;

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
        return new HystrixVishyOpenMrcHttpRequestService(httpRequestMapper(), openMrcRequestConsumer());
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
