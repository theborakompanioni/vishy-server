package org.tbk.vishy.properties.provider.geolocation.resolver;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.tbk.vishy.properties.provider.geolocation.GeoLocation;
import org.tbk.vishy.properties.provider.geolocation.GeoLocationUtils;
import org.tbk.vishy.utils.ip.RemoteAddressExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.Consumer;

public class GeoLocationResolverHandlerInterceptor extends HandlerInterceptorAdapter {
    private final static String DEFAULT_LOCAL_IP_SUBSTITUDE = "8.8.8.8";

    private final GeoLocationResolver geoLocationResolver;
    private final RemoteAddressExtractor remoteAddressExtractor;

    public GeoLocationResolverHandlerInterceptor(GeoLocationResolver geoLocationResolver) {
        this(geoLocationResolver, DEFAULT_LOCAL_IP_SUBSTITUDE);
    }

    public GeoLocationResolverHandlerInterceptor(GeoLocationResolver geoLocationResolver, String localIpSubstitude) {
        this.geoLocationResolver = geoLocationResolver;
        this.remoteAddressExtractor = RemoteAddressExtractor
            .withLocalSubstitude(localIpSubstitude);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Consumer<GeoLocation> setRequestAttribute = (geoLocation) -> request.setAttribute(GeoLocationUtils.CURRENT_GEOLOCATION_ATTRIBUTE, geoLocation);

        Optional.ofNullable(request)
            .flatMap(remoteAddressExtractor::getIpAddress)
            .flatMap(geoLocationResolver::getLocation)
            .ifPresent(setRequestAttribute);

        return true;
    }
}
