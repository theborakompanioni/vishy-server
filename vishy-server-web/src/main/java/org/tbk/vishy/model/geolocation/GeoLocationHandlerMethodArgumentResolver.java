package org.tbk.vishy.model.geolocation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.tbk.vishy.utils.HttpServletRequestUtils;
import org.tbk.vishy.utils.ip.RemoteAddressExtractor;

public class GeoLocationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final static String DEFAULT_LOCAL_IP_SUBSTITUDE = "8.8.8.8";

    private final GeoLocationProvider geoLocationProvider;
    private final RemoteAddressExtractor remoteAddressExtractor;

    public GeoLocationHandlerMethodArgumentResolver(GeoLocationProvider geoLocationProvider) {
        this(geoLocationProvider, DEFAULT_LOCAL_IP_SUBSTITUDE);
    }

    public GeoLocationHandlerMethodArgumentResolver(GeoLocationProvider geoLocationProvider, String localIpSubstitude) {
        this.geoLocationProvider = geoLocationProvider;
        this.remoteAddressExtractor = RemoteAddressExtractor
            .withLocalSubstitude(localIpSubstitude);
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return GeoLocation.class.isAssignableFrom(parameter.getParameterType());
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        return HttpServletRequestUtils.getCurrentHttpRequest()
            .flatMap(remoteAddressExtractor::getIpAddress)
            .flatMap(geoLocationProvider::getLocation)
            .orElse(null);
    }
}
