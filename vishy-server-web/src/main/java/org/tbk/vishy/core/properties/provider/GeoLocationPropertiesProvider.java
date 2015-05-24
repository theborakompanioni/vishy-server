package org.tbk.vishy.core.properties.provider;

import com.google.common.collect.ImmutableMap;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;
import org.tbk.vishy.model.geolocation.GeoLocation;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GeoLocationPropertiesProvider implements PropertyProvider {

    private static Function<GeoLocation, Map<String, Object>> LOCATION_AS_MAP = (location) ->
        ImmutableMap.<String, Object>builder()
            .put("geolocation", ImmutableMap.<String, Object>builder()
                .put("lat", location.getLat())
                .put("lng", location.getLng())
                .put("display_country", location.getCountry())
                .put("country", location.getCountryCode())
                .put("city", location.getCity())
                .put("display_region", location.getRegion())
                .put("region", location.getRegionCode())
                .put("timezone", location.getTimezone())
                .put("zip_code", location.getZipCode())
                .build())
            .build();

    private final GeoLocation location;

    public GeoLocationPropertiesProvider(GeoLocation location) {
        this.location = location;
    }

    @Override
    public Optional<Map<String, Object>> get() {
        return Optional.ofNullable(location)
            .map(LOCATION_AS_MAP);
    }
}
