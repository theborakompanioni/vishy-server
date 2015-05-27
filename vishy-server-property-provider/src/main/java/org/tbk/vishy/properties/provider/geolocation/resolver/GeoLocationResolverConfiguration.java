package org.tbk.vishy.properties.provider.geolocation.resolver;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by void on 08.03.15.
 */
public interface GeoLocationResolverConfiguration extends WebMvcConfigurer {

    GeoLocationResolver geoLocationSupplier();

    default GeoLocationHandlerMethodArgumentResolver geoLocationHandlerMethodArgumentResolver() {
        return new GeoLocationHandlerMethodArgumentResolver(geoLocationSupplier());
    }

    default GeoLocationResolverHandlerInterceptor geoLocationResolverHandlerInterceptor() {
        return new GeoLocationResolverHandlerInterceptor(geoLocationSupplier());
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
