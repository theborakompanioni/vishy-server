package org.tbk.vishy.properties.provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.impl.LocaleRequestInterceptor;
import org.tbk.openmrc.impl.ReferrerRequestInterceptor;
import org.tbk.openmrc.impl.UserAgentRequestInterceptor;
import org.tbk.vishy.properties.provider.browser.BrowserRequestInterceptor;
import org.tbk.vishy.properties.provider.device.DeviceRequestInterceptor;
import org.tbk.vishy.properties.provider.geolocation.GeoLocationRequestInterceptor;
import org.tbk.vishy.properties.provider.geolocation.impl.freegeoip.resolver.FreeGeoIpLocationResolver;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolver;
import org.tbk.vishy.properties.provider.operatingsystem.OperatingSystemRequestInterceptor;

/**
 * Created by void on 21.06.15.
 */
@Configuration
public class ExtensionsConfiguration {
    
    @Bean
    public LocaleRequestInterceptor localeRequestInterceptor() {
        return new LocaleRequestInterceptor();
    }

    @Bean
    public UserAgentRequestInterceptor userAgentRequestInterceptor() {
        return new UserAgentRequestInterceptor();
    }

    @Bean
    public ReferrerRequestInterceptor referrerRequestInterceptor() {
        return new ReferrerRequestInterceptor();
    }

    @Bean
    public OperatingSystemRequestInterceptor operatingSystemRequestInterceptor() {
        return new OperatingSystemRequestInterceptor();
    }

    @Bean
    public DeviceRequestInterceptor deviceRequestInterceptor() {
        return new DeviceRequestInterceptor();
    }

    @Bean
    public BrowserRequestInterceptor browserRequestInterceptor() {
        return new BrowserRequestInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(GeoLocationResolver.class)
    public FreeGeoIpLocationResolver geoLocationSupplier() {
        return new FreeGeoIpLocationResolver();
    }

    @Bean
    public GeoLocationRequestInterceptor geoLocationRequestInterceptor(GeoLocationResolver geoLocationResolver) {
        return new GeoLocationRequestInterceptor(geoLocationResolver);
    }


}
