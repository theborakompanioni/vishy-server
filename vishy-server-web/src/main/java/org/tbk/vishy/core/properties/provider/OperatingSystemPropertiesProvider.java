package org.tbk.vishy.core.properties.provider;

import com.google.common.collect.ImmutableMap;
import eu.bitwalker.useragentutils.OperatingSystem;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by void on 02.05.15.
 */
public class OperatingSystemPropertiesProvider implements PropertyProvider {

    private static Function<OperatingSystem, Map<String, Object>> OS_AS_MAP = (operatingSystem) ->
        ImmutableMap.<String, Object>builder()
            .put("os", ImmutableMap.<String, Object>builder()
                .put("name", operatingSystem.getName())
                .put("groupName", operatingSystem.getGroup().getName())
                .put("manufacturer", operatingSystem.getGroup().getManufacturer().getName())
                .build())
            .build();

    private final OperatingSystem operatingSystem;

    public OperatingSystemPropertiesProvider(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public Optional<Map<String, Object>> get() {
        return Optional.ofNullable(operatingSystem)
            .map(OS_AS_MAP);
    }
}
