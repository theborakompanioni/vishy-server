package org.tbk.vishy.model.geolocation;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.tbk.vishy.utils.ip.RemoteAddressExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.function.Consumer;

public class GeoLocationResolverHandlerInterceptor extends HandlerInterceptorAdapter {
    private final static String DEFAULT_LOCAL_IP_SUBSTITUDE = "8.8.8.8";

    private final GeoLocationProvider geoLocationProvider;
    private final RemoteAddressExtractor remoteAddressExtractor;

    public GeoLocationResolverHandlerInterceptor(GeoLocationProvider geoLocationProvider) {
        this(geoLocationProvider, DEFAULT_LOCAL_IP_SUBSTITUDE);
    }

    public GeoLocationResolverHandlerInterceptor(GeoLocationProvider geoLocationProvider, String localIpSubstitude) {
        this.geoLocationProvider = geoLocationProvider;
        this.remoteAddressExtractor = RemoteAddressExtractor
            .withLocalSubstitude(localIpSubstitude);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Consumer<GeoLocation> setRequestAttribute = (geoLocation) -> request.setAttribute(GeoLocationUtils.CURRENT_GEOLOCATION_ATTRIBUTE, geoLocation);

        Optional.ofNullable(request)
            .flatMap(remoteAddressExtractor::getIpAddress)
            .flatMap(geoLocationProvider::getLocation)
            .ifPresent(setRequestAttribute);

        return true;
    }
}
