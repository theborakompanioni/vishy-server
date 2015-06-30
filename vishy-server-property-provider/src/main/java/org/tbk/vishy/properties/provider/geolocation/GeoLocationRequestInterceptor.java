package org.tbk.vishy.properties.provider.geolocation;

import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.impl.ExtensionHttpRequestInterceptorSupport;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolver;
import org.tbk.vishy.utils.ip.RemoteAddressExtractor;

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

    private final static String DEFAULT_LOCAL_IP_SUBSTITUDE = "8.8.8.8";

    private final GeoLocationResolver geoLocationResolver;
    private final RemoteAddressExtractor remoteAddressExtractor;

    public GeoLocationRequestInterceptor(GeoLocationResolver geoLocationResolver) {
        this(geoLocationResolver, Optional.of(UNKNOWN));
    }

    public GeoLocationRequestInterceptor(GeoLocationResolver geoLocationResolver, Optional<OpenMrcExtensions.GeoLocation> defaultValue) {
        super(OpenMrcExtensions.GeoLocation.geolocation, Objects.requireNonNull(defaultValue));
        this.geoLocationResolver = geoLocationResolver;
        this.remoteAddressExtractor = RemoteAddressExtractor
                .withLocalSubstitude(DEFAULT_LOCAL_IP_SUBSTITUDE);
    }

    @Override
    protected Optional<OpenMrcExtensions.GeoLocation> extract(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest)
                .flatMap(remoteAddressExtractor::getIpAddress)
                .flatMap(geoLocationResolver::getLocation)
                .map(TO_PROTO);
    }

}
