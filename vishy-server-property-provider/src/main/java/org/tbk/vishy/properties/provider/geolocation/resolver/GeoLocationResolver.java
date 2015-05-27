package org.tbk.vishy.properties.provider.geolocation.resolver;

import org.tbk.vishy.properties.provider.geolocation.GeoLocation;

import java.util.Optional;

/**
 * Created by void on 02.05.15.
 */
public interface GeoLocationResolver {
    Optional<GeoLocation> getLocation(String ipAddress);
}
