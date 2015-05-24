package org.tbk.vishy.model.geolocation;

import java.util.Optional;

/**
 * Created by void on 02.05.15.
 */
public interface GeoLocationProvider {
    Optional<GeoLocation> getLocation(String ipAddress);
}
