package org.tbk.vishy.properties.provider.operatingsystem;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.properties.provider.GenericPropertyProviderFactory;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by void on 27.05.15.
 */
@Slf4j
@Configuration
public class OperatingSystemProviderConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public PropertyProviderFactory operatingSystemPropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(ExtractUserAgent.fromHttpRequest)
                                .flatMap(Supplier::get)
                                .map(UserAgent::getOperatingSystem)
                                .map(OperatingSystemPropertiesProvider::new)
                                .flatMap(OperatingSystemPropertiesProvider::get));
    }
}
