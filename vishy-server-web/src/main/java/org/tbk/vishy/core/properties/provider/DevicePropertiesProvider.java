package org.tbk.vishy.core.properties.provider;

import com.google.common.collect.ImmutableMap;
import eu.bitwalker.useragentutils.DeviceType;
import org.springframework.mobile.device.Device;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Created by void on 02.05.15.
 */
public class DevicePropertiesProvider implements PropertyProvider {

    private static BiFunction<Device, DeviceType, Map<String, Object>> DEVICE_AS_MAP =
        (device, deviceType) ->
            ImmutableMap.<String, Object>builder()
                .put("device", ImmutableMap.<String, Object>builder()
                    .put("type", deviceType.toString())
                    .put("name", deviceType.getName())
                    .put("normal", device.isNormal())
                    .put("tablet", device.isTablet())
                    .put("mobile", device.isMobile())
                    .build())
                .build();

    private final DeviceType deviceType;
    private final Device device;

    public DevicePropertiesProvider(DeviceType deviceType, Device device) {
        this.deviceType = deviceType;
        this.device = device;
    }

    @Override
    public Optional<Map<String, Object>> get() {
        boolean bothPresent = Optional.ofNullable(deviceType)
            .flatMap(deviceType -> Optional.ofNullable(device))
            .isPresent();

        if (!bothPresent) {
            return Optional.empty();
        }

        return Optional.of(deviceType)
            .flatMap(deviceType -> Optional.ofNullable(device))
            .map(device -> DEVICE_AS_MAP.apply(device, deviceType));
    }
}
