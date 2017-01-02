package org.tbk.vishy.properties.provider.geolocation;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.github.theborakompanioni.openmrc.spring.impl.ExtensionHttpRequestInterceptorSupport;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import io.reactivex.Observable;
import org.tbk.vishy.properties.provider.geolocation.impl.freegeoip.GeoLocationCommand;
import org.tbk.vishy.properties.provider.geolocation.resolver.GeoLocationResolver;
import org.tbk.vishy.utils.ip.RemoteAddressExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Function;

public class GeoLocationRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.GeoLocation> {

    private final static HystrixCommandGroupKey DEFAULT_KEY = HystrixCommandGroupKey.Factory
            .asKey("GeoLocationRequestInterceptor");

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

    private final static String DEFAULT_LOCAL_IP_SUBSTITUTE = "8.8.8.8";

    private final GeoLocationResolver geoLocationResolver;
    private final RemoteAddressExtractor remoteAddressExtractor;
    private final HystrixCommand.Setter setter;

    public GeoLocationRequestInterceptor(GeoLocationResolver geoLocationResolver) {
        this(geoLocationResolver, UNKNOWN);
    }

    public GeoLocationRequestInterceptor(GeoLocationResolver geoLocationResolver, OpenMrcExtensions.GeoLocation defaultValue) {
        super(OpenMrcExtensions.GeoLocation.geolocation, Optional.ofNullable(defaultValue));
        this.geoLocationResolver = geoLocationResolver;
        this.remoteAddressExtractor = RemoteAddressExtractor
                .withLocalSubstitute(DEFAULT_LOCAL_IP_SUBSTITUTE);

        this.setter = HystrixCommand.Setter.withGroupKey(DEFAULT_KEY)
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withRequestLogEnabled(false)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(1_000)
                        .withExecutionTimeoutInMilliseconds(5_000));
    }

    @Override
    protected Observable<OpenMrcExtensions.GeoLocation> extract(HttpServletRequest httpServletRequest) {
        final Optional<GeoLocationCommand> geoLocationCommand = Optional.ofNullable(httpServletRequest)
                .flatMap(remoteAddressExtractor::getIpAddress)
                .map(ipAddress -> new GeoLocationCommand(setter, geoLocationResolver, ipAddress));

        return Observable.defer(() -> Observable.just(geoLocationCommand)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(HystrixCommand::execute)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TO_PROTO::apply));

    }

}
