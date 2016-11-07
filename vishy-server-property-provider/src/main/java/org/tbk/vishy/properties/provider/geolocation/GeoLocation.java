package org.tbk.vishy.properties.provider.geolocation;

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
