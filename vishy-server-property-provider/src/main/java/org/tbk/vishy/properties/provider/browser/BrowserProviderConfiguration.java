package org.tbk.vishy.properties.provider.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.properties.provider.GenericPropertyProviderFactory;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by void on 27.05.15.
 */
@Slf4j
@Configuration
public class BrowserProviderConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public PropertyProviderFactory browserPropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(ExtractUserAgent.fromHttpRequest)
                                .map(BrowserPropertiesProvider::new)
                                .flatMap(BrowserPropertiesProvider::get));
    }
}
