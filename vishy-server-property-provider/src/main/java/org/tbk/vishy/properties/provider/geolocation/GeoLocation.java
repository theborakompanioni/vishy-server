package org.tbk.vishy.properties.provider.geolocation;

/**
 * Created by void on 02.05.15.
 */
public interface GeoLocation {
    double getLat();

    double getLng();

    String getCountry();

    String getCountryCode();

    String getRegion();

    String getRegionCode();

    String getCity();

    String getZipCode();

    String getTimezone();
}
