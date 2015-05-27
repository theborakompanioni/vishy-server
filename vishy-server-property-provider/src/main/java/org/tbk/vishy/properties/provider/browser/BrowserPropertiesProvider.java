package org.tbk.vishy.properties.provider.browser;

import com.google.common.collect.ImmutableMap;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.tbk.openmrc.core.properties.provider.PropertyProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Created by void on 02.05.15.
 */
public class BrowserPropertiesProvider implements PropertyProvider {

    private static BiFunction<Browser, Version, Map<String, Object>> BROWSER_AS_MAP =
        (browser, version) ->
            ImmutableMap.<String, Object>builder()
                .put("browser", ImmutableMap.<String, Object>builder()
                    .put("name", browser.getGroup().getName())
                    .put("type", browser.getGroup().getBrowserType().getName())
                    .put("version", version.getVersion())
                    .put("majorVersion", version.getMajorVersion())
                    .build())
                .build();

    private final Optional<Supplier<Optional<UserAgent>>> userAgentSupplier;

    public BrowserPropertiesProvider(Supplier<Optional<UserAgent>> userAgentSupplier) {
        this.userAgentSupplier = Optional.ofNullable(userAgentSupplier);
    }

    @Override
    public Optional<Map<String, Object>> get() {
        return userAgentSupplier
            .flatMap(Supplier::get)
            .map(ua -> BROWSER_AS_MAP.apply(ua.getBrowser(), ua.getBrowserVersion()));
    }
}
