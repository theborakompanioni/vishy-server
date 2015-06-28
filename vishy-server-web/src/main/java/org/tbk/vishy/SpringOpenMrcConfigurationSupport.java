package org.tbk.vishy;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestInterceptor;
import org.tbk.openmrc.mapper.OpenMrcHttpRequestMapper;
import org.tbk.openmrc.web.OpenMrcHttpRequestService;
import org.tbk.openmrc.web.OpenMrcWebConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by void on 28.06.15.
 */
@Slf4j
public abstract class SpringOpenMrcConfigurationSupport extends OpenMrcWebConfigurationSupport {

    @Override
    @Bean
    public MetricRegistry metricsRegistry() {
        return super.metricsRegistry();
    }

    @Override
    @Bean
    public ExtensionRegistry extensionRegistry() {
        return super.extensionRegistry();
    }

    @Override
    @Bean
    public OpenMrcHttpRequestMapper httpRequestMapper() {
        return super.httpRequestMapper();
    }

    @Override
    @Bean
    public OpenMrcHttpRequestService httpRequestService() {
        return super.httpRequestService();
    }

    @Override
    @Bean
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        return super.openMrcRequestConsumer();
    }

    @Override
    @Bean
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
        return super.httpRequestInterceptor();
    }
}