package org.tbk.vishy.model.geolocation;

import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class GeoLocationUtils {
    public static final String CURRENT_GEOLOCATION_ATTRIBUTE = "currentGeoLocation";

    private GeoLocationUtils() {
        throw new UnsupportedOperationException();
    }

    public static Optional<GeoLocation> getCurrentGeoLocation(HttpServletRequest request) {
        return Optional.ofNullable((GeoLocation) request.getAttribute(CURRENT_GEOLOCATION_ATTRIBUTE));
    }

    public static Optional<GeoLocation> getCurrentGeoLocation(RequestAttributes attributes) {
        return Optional.ofNullable((GeoLocation) attributes.getAttribute(CURRENT_GEOLOCATION_ATTRIBUTE, 0));
    }
}
