package org.tbk.vishy.properties.provider.browser;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.github.theborakompanioni.openmrc.impl.ExtensionHttpRequestInterceptorSupport;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.Version;
import org.tbk.vishy.utils.ExtractUserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Strings.nullToEmpty;

public class BrowserRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Browser> {

    private static final Version UNKNOWN_VERSION = new Version("?", "?", "?");
    private static final OpenMrcExtensions.Browser UNKNOWN = OpenMrcExtensions.Browser.newBuilder()
            .setName("?")
            .setType("?")
            .setVersion(UNKNOWN_VERSION.getVersion())
            .setMajorVersion(UNKNOWN_VERSION.getMajorVersion())
            .setManufacturer("?")
            .build();

    private static final BiFunction<Browser, Version, OpenMrcExtensions.Browser> TO_PROTO = (browser, version) ->
            OpenMrcExtensions.Browser.newBuilder()
                    .setName(nullToEmpty(browser.getGroup().getName()))
                    .setType(nullToEmpty(browser.getGroup().getBrowserType().getName()))
                    .setVersion(nullToEmpty(version.getVersion()))
                    .setMajorVersion(nullToEmpty(version.getVersion()))
                    .setManufacturer(nullToEmpty(browser.getGroup().getManufacturer().getName()))
                    .build();

    public BrowserRequestInterceptor() {
        this(UNKNOWN);
    }

    public BrowserRequestInterceptor(OpenMrcExtensions.Browser defaultValue) {
        super(OpenMrcExtensions.Browser.browser, Optional.ofNullable(defaultValue));
    }

    @Override
    protected Optional<OpenMrcExtensions.Browser> extract(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest)
                .map(ExtractUserAgent.fromHttpRequest)
                .flatMap(Supplier::get)
                .map(ua -> TO_PROTO.apply(ua.getBrowser(), firstNonNull(ua.getBrowserVersion(), UNKNOWN_VERSION)));
    }

}
