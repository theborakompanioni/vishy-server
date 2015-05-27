package org.tbk.vishy.properties.provider.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;

import javax.annotation.PostConstruct;

/**
 * Created by void on 27.05.15.
 */
@Slf4j
@Configuration
public class DeviceProviderConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public PropertyProviderFactory devicePropertyProviderFactory() {
        return new DevicePropertiesProviderFactory();
    }
}
