package org.tbk.vishy.properties.provider.geolocation.impl.freegeoip;

import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.vishy.properties.provider.geolocation.GeoLocation;
import org.tbk.vishy.properties.provider.geolocation.GeoLocationPropertiesProvider;
import org.tbk.vishy.properties.provider.geolocation.GeoLocationUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by void on 27.05.15.
 */
public class FreeGeoIpLocationProviderFactory implements PropertyProviderFactory {

    private Function<HttpServletRequest, Supplier<Optional<GeoLocation>>> geoLocationFromHttpRequest =
            request -> (Supplier<Optional<GeoLocation>>) () -> GeoLocationUtils.getCurrentGeoLocation(request);

    @Override
    public PropertyProvider fromRequest(OpenMrcRequestContext requestOrNull) {
        return () -> Optional.ofNullable(requestOrNull)
                .flatMap(OpenMrcRequestContext::httpRequest)
                .map(geoLocationFromHttpRequest)
                .flatMap(Supplier::get)
                .map(GeoLocationPropertiesProvider::new)
                .flatMap(GeoLocationPropertiesProvider::get);
    }
}
