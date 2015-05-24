package org.tbk.vishy.model.geolocation;

import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tbk.vishy.model.geolocation.impl.freegeoip.FreeGeoIpLocationProvider;

import java.util.List;

/**
 * Created by void on 08.03.15.
 */
public interface GeoLocationHandlerMethodResolverConfiguration extends WebMvcConfigurer {

    @Bean
    FreeGeoIpLocationProvider freeGeoIpService();

    default GeoLocationHandlerMethodArgumentResolver geoLocationHandlerMethodArgumentResolver() {
        return new GeoLocationHandlerMethodArgumentResolver(freeGeoIpService());
    }

    default GeoLocationResolverHandlerInterceptor geoLocationResolverHandlerInterceptor() {
        return new GeoLocationResolverHandlerInterceptor(freeGeoIpService());
    }

    @Override
    default void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(geoLocationHandlerMethodArgumentResolver());
    }

    @Override
    default void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(geoLocationResolverHandlerInterceptor());
    }
}
