package org.tbk.vishy.properties.provider.browser;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.Version;
import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.impl.ExtensionHttpRequestInterceptorSupport;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Created by void on 20.06.15.
 */
public class BrowserRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Browser> {

    private static final OpenMrcExtensions.Browser UNKNOWN = OpenMrcExtensions.Browser.newBuilder()
            .setName("?")
            .setManufacturer("?")
            .setVersion("?")
            .build();

    private static final BiFunction<Browser, Version, OpenMrcExtensions.Browser> TO_PROTO = (browser, version) ->
            OpenMrcExtensions.Browser.newBuilder()
                    .setName(browser.getGroup().getName())
                    .setType(browser.getGroup().getBrowserType().getName())
                    .setVersion(version.getVersion())
                    .setMajorVersion(version.getVersion())
                    .setManufacturer(browser.getGroup().getManufacturer().getName())
                    .build();

    public BrowserRequestInterceptor() {
        this(Optional.of(UNKNOWN));
    }

    public BrowserRequestInterceptor(Optional<OpenMrcExtensions.Browser> defaultValue) {
        super(OpenMrcExtensions.Browser.browser, Objects.requireNonNull(defaultValue));
    }

    @Override
    protected Optional<OpenMrcExtensions.Browser> extract(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest)
                .map(ExtractUserAgent.fromHttpRequest)
                .flatMap(Supplier::get)
                .map(ua -> TO_PROTO.apply(ua.getBrowser(), ua.getBrowserVersion()));
    }

}
