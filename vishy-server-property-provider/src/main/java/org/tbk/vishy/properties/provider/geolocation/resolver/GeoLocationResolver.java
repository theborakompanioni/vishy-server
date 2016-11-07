package org.tbk.vishy.properties.provider.geolocation.resolver;

import org.tbk.vishy.properties.provider.geolocation.GeoLocation;

import java.util.Optional;

public interface GeoLocationResolver {
    Optional<GeoLocation> getLocation(String ipAddress);
}
