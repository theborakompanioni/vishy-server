package org.tbk.vishy.properties.provider.device;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by void on 02.05.15.
 */
public class DevicePropertiesProviderFactory implements PropertyProviderFactory {

    @Override
    public PropertyProvider fromRequest(OpenMrcRequestContext context) {
        Optional<HttpServletRequest> request = Optional.ofNullable(context)
                .flatMap(OpenMrcRequestContext::httpRequest);

        Device deviceOrNull = request.map(DeviceUtils::getCurrentDevice).orElse(null);

        return () -> request
                .map(ExtractUserAgent.fromHttpRequest)
                .flatMap(Supplier::get)
                .map(UserAgent::getOperatingSystem)
                .map(OperatingSystem::getDeviceType)
                .map(deviceType -> new DevicePropertiesProvider(deviceType, deviceOrNull))
                .flatMap(DevicePropertiesProvider::get);
    }
}
