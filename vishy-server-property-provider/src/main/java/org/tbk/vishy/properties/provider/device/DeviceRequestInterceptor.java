package org.tbk.vishy.properties.provider.device;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.github.theborakompanioni.openmrc.spring.impl.ExtensionHttpRequestInterceptorSupport;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.reactivex.Observable;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DeviceRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Device> {

    private static final OpenMrcExtensions.Device UNKNOWN = OpenMrcExtensions.Device.newBuilder()
            .setName("?")
            .setType("?")
            .setNormal(true)
            .setMobile(false)
            .setTablet(false)
            .build();

    private static final Device NORMAL_INSTANCE = new Device() {
        @Override
        public boolean isNormal() {
            return true;
        }

        @Override
        public boolean isMobile() {
            return false;
        }

        @Override
        public boolean isTablet() {
            return false;
        }
    };

    private static final BiFunction<Device, DeviceType, OpenMrcExtensions.Device> TO_PROTO = (device, type) ->
            OpenMrcExtensions.Device.newBuilder()
                    .setName(type.getName())
                    .setType(type.toString())
                    .setNormal(device.isNormal())
                    .setMobile(device.isMobile())
                    .setTablet(device.isTablet())
                    .build();

    public DeviceRequestInterceptor() {
        this(UNKNOWN);
    }

    public DeviceRequestInterceptor(OpenMrcExtensions.Device defaultValue) {
        super(OpenMrcExtensions.Device.device, Optional.ofNullable(defaultValue));
    }

    @Override
    protected Observable<OpenMrcExtensions.Device> extract(HttpServletRequest httpServletRequest) {
        Device device = Optional.ofNullable(httpServletRequest)
                .map(DeviceUtils::getCurrentDevice)
                .orElse(NORMAL_INSTANCE);

        return Optional.ofNullable(httpServletRequest)
                .map(ExtractUserAgent.fromHttpRequest)
                .flatMap(Supplier::get)
                .map(UserAgent::getOperatingSystem)
                .map(OperatingSystem::getDeviceType)
                .map(deviceType -> TO_PROTO.apply(device, deviceType))
                .map(Observable::just)
                .orElse(Observable.empty());
    }

}
