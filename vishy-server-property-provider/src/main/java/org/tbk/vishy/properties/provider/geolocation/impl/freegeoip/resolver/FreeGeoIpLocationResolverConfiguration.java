package org.tbk.vishy.properties.provider.geolocation.impl.freegeoip.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationHandlerMethodArgumentResolver;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolverConfiguration;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolverHandlerInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by void on 08.03.15.
 */
@Slf4j
@Configuration
public class FreeGeoIpLocationResolverConfiguration extends WebMvcConfigurerAdapter
        implements GeoLocationResolverConfiguration {

    @PostConstruct
    public void init() {
        log.info("initialize");
    }

    @Bean
    public FreeGeoIpLocationResolver geoLocationSupplier() {
        return new FreeGeoIpLocationResolver();
    }

    @Bean
    public GeoLocationHandlerMethodArgumentResolver freeGeoIpHandlerMethodArgumentResolver() {
        return new GeoLocationHandlerMethodArgumentResolver(geoLocationSupplier());
    }

    @Bean
    public GeoLocationResolverHandlerInterceptor freeGeoIpResolverHandlerInterceptor() {
        return new GeoLocationResolverHandlerInterceptor(geoLocationSupplier());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(freeGeoIpResolverHandlerInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(freeGeoIpHandlerMethodArgumentResolver());
    }
}
