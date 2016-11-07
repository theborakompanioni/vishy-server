package org.tbk.vishy.properties.provider.geolocation.impl.freegeoip;

import com.netflix.hystrix.HystrixCommand;
import org.tbk.vishy.properties.provider.geolocation.GeoLocation;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolver;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class GeoLocationCommand extends HystrixCommand<Optional<GeoLocation>> {

    private final Optional<GeoLocation> fallback;
    private final GeoLocationResolver resolver;
    private final String ipAddress;

    public GeoLocationCommand(Setter setter, GeoLocationResolver resolver, String ipAddress) {
        this(setter, resolver, ipAddress, null);
    }

    public GeoLocationCommand(Setter setter, GeoLocationResolver resolver, String ipAddress, GeoLocation fallback) {
        super(setter);
        this.resolver = requireNonNull(resolver);
        this.ipAddress = requireNonNull(ipAddress);
        this.fallback = Optional.ofNullable(fallback);
    }

    @Override
    protected Optional<GeoLocation> run() throws Exception {
        return resolver.getLocation(ipAddress);
    }

    @Override
    protected Optional<GeoLocation> getFallback() {
        return fallback;
    }
}
