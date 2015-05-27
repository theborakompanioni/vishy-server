package org.tbk.vishy.properties.provider.geolocation.impl.freegeoip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.vishy.properties.provider.geolocation.GeoLocationProviderConfiguration;

import javax.annotation.PostConstruct;

/**
 * Created by void on 27.05.15.
 */
@Slf4j
@Configuration
public class FreeGeoIpLocationProviderConfiguration implements GeoLocationProviderConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public PropertyProviderFactory geoLocationPropertyProviderFactory() {
        return new FreeGeoIpLocationProviderFactory();
    }
}
