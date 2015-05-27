package org.tbk.vishy.properties.provider.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.properties.provider.GenericPropertyProviderFactory;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by void on 27.05.15.
 */
@Slf4j
@Configuration
public class LocaleProviderConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public PropertyProviderFactory localePropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(HttpServletRequest::getLocale)
                                .map(LocalePropertiesProvider::new)
                                .flatMap(LocalePropertiesProvider::get));
    }
}
