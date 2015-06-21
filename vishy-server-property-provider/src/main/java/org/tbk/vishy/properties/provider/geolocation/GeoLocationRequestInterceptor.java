package org.tbk.vishy.properties.provider.geolocation;

import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.impl.ExtensionHttpRequestInterceptorSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by void on 20.06.15.
 */
public class GeoLocationRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.GeoLocation> {

    private static final OpenMrcExtensions.GeoLocation UNKNOWN = OpenMrcExtensions.GeoLocation.newBuilder()
            .setLat(0f)
            .setLng(0f)
            .setCountry("?")
            .setDisplayCountry("?")
            .setRegion("?")
            .setDisplayRegion("?")
            .setCity("?")
            .setZipCode("?")
            .setTimezone("?")
            .build();

    private static final Function<GeoLocation, OpenMrcExtensions.GeoLocation> TO_PROTO = (geoLocation) ->
            OpenMrcExtensions.GeoLocation.newBuilder()
                    .setLat((float) geoLocation.getLat())
                    .setLng((float) geoLocation.getLng())
                    .setCountry(geoLocation.getCountryCode())
                    .setDisplayCountry(geoLocation.getCountry())
                    .setRegion(geoLocation.getRegionCode())
                    .setDisplayRegion(geoLocation.getRegion())
                    .setCity(geoLocation.getCity())
                    .setZipCode(geoLocation.getZipCode())
                    .setTimezone(geoLocation.getTimezone())
                    .build();

    public GeoLocationRequestInterceptor() {
        this(Optional.of(UNKNOWN));
    }

    public GeoLocationRequestInterceptor(Optional<OpenMrcExtensions.GeoLocation> defaultValue) {
        super(OpenMrcExtensions.GeoLocation.geolocation, Objects.requireNonNull(defaultValue));
    }

    @Override
    protected Optional<OpenMrcExtensions.GeoLocation> extract(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest)
                .flatMap(GeoLocationUtils::getCurrentGeoLocation)
                .map(TO_PROTO);
    }

}
