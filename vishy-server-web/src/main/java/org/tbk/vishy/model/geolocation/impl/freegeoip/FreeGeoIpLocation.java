package org.tbk.vishy.model.geolocation.impl.freegeoip;


import org.tbk.vishy.model.geolocation.GeoLocation;

public class FreeGeoIpLocation implements GeoLocation {
    private final double lat;
    private final double lng;
    private final String country;
    private final String countryCode;
    private final String region;
    private final String regionCode;
    private final String city;
    private final String zipCode;
    private final String timezone;

    private FreeGeoIpLocation(double lat, double lng, String country, String countryCode, String region, String regionCode, String city, String zipCode, String timezone) {
        this.lat = lat;
        this.lng = lng;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionCode = regionCode;
        this.city = city;
        this.zipCode = zipCode;
        this.timezone = timezone;
    }

    @Override
    public double getLat() {
        return this.lat;
    }

    @Override
    public double getLng() {
        return this.lng;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public String getCountryCode() {
        return this.countryCode;
    }

    @Override
    public String getRegion() {
        return this.region;
    }

    @Override
    public String getRegionCode() {
        return this.regionCode;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public String getTimezone() {
        return this.timezone;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FreeGeoIpLocation)) return false;
        final FreeGeoIpLocation other = (FreeGeoIpLocation) o;
        if (Double.compare(this.lat, other.lat) != 0) return false;
        if (Double.compare(this.lng, other.lng) != 0) return false;
        final Object this$country = this.country;
        final Object other$country = other.country;
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) return false;
        final Object this$countryCode = this.countryCode;
        final Object other$countryCode = other.countryCode;
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        final Object this$region = this.region;
        final Object other$region = other.region;
        if (this$region == null ? other$region != null : !this$region.equals(other$region)) return false;
        final Object this$regionCode = this.regionCode;
        final Object other$regionCode = other.regionCode;
        if (this$regionCode == null ? other$regionCode != null : !this$regionCode.equals(other$regionCode))
            return false;
        final Object this$city = this.city;
        final Object other$city = other.city;
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
        final Object this$zipCode = this.zipCode;
        final Object other$zipCode = other.zipCode;
        if (this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode)) return false;
        final Object this$timezone = this.timezone;
        final Object other$timezone = other.timezone;
        if (this$timezone == null ? other$timezone != null : !this$timezone.equals(other$timezone)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $lat = Double.doubleToLongBits(this.lat);
        result = result * PRIME + (int) ($lat >>> 32 ^ $lat);
        final long $lng = Double.doubleToLongBits(this.lng);
        result = result * PRIME + (int) ($lng >>> 32 ^ $lng);
        final Object $country = this.country;
        result = result * PRIME + ($country == null ? 0 : $country.hashCode());
        final Object $countryCode = this.countryCode;
        result = result * PRIME + ($countryCode == null ? 0 : $countryCode.hashCode());
        final Object $region = this.region;
        result = result * PRIME + ($region == null ? 0 : $region.hashCode());
        final Object $regionCode = this.regionCode;
        result = result * PRIME + ($regionCode == null ? 0 : $regionCode.hashCode());
        final Object $city = this.city;
        result = result * PRIME + ($city == null ? 0 : $city.hashCode());
        final Object $zipCode = this.zipCode;
        result = result * PRIME + ($zipCode == null ? 0 : $zipCode.hashCode());
        final Object $timezone = this.timezone;
        result = result * PRIME + ($timezone == null ? 0 : $timezone.hashCode());
        return result;
    }

    public String toString() {
        return "FreeGeoIpLocation(lat=" + this.lat + ", lng=" + this.lng + ", country=" + this.country + ", countryCode=" + this.countryCode + ", region=" + this.region + ", regionCode=" + this.regionCode + ", city=" + this.city + ", zipCode=" + this.zipCode + ", timezone=" + this.timezone + ")";
    }

    public static class Builder {
        private double lat;
        private double lng;
        private String country;
        private String countryCode;
        private String region;
        private String regionCode;
        private String city;
        private String zipCode;
        private String timezone;

        public Builder setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public Builder setLng(double lng) {
            this.lng = lng;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setRegionCode(String regionCode) {
            this.regionCode = regionCode;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder setTimezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public FreeGeoIpLocation build() {
            return new FreeGeoIpLocation(lat, lng, country, countryCode, region, regionCode, city, zipCode, timezone);
        }
    }
}
