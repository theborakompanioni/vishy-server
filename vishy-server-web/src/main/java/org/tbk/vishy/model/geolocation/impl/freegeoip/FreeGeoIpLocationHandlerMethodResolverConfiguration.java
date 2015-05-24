package org.tbk.vishy.model.geolocation.impl.freegeoip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.vishy.model.geolocation.GeoLocationHandlerMethodArgumentResolver;
import org.tbk.vishy.model.geolocation.GeoLocationHandlerMethodResolverConfiguration;
import org.tbk.vishy.model.geolocation.GeoLocationResolverHandlerInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by void on 08.03.15.
 */
@Slf4j
@Configuration
public class FreeGeoIpLocationHandlerMethodResolverConfiguration extends WebMvcConfigurerAdapter
        implements GeoLocationHandlerMethodResolverConfiguration {

    @PostConstruct
    public void init() {
        log.info("GeoLocationHandlerMethodResolverConfiguration init");
    }

    @Bean
    public FreeGeoIpLocationProvider freeGeoIpService() {
        return new FreeGeoIpLocationProvider();
    }

    @Bean
    public GeoLocationHandlerMethodArgumentResolver freeGeoIpHandlerMethodArgumentResolver() {
        return new GeoLocationHandlerMethodArgumentResolver(freeGeoIpService());
    }

    @Bean
    public GeoLocationResolverHandlerInterceptor freeGeoIpResolverHandlerInterceptor() {
        return new GeoLocationResolverHandlerInterceptor(freeGeoIpService());
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
