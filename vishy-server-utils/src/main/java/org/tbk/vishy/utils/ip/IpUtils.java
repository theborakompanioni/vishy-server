package org.tbk.vishy.utils.ip;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

public final class IpUtils {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',')
            .omitEmptyStrings()
            .trimResults();

    private IpUtils() {
        throw new UnsupportedOperationException();
    }

    public static Optional<String> getIpAddress(HttpServletRequest request) {
        Optional<String> stringStream = Collections.list(request.getHeaderNames()).stream()
                .filter(HttpHeaders.X_FORWARDED_FOR::equalsIgnoreCase)
                .map(request::getHeader)
                .flatMap(headerValue -> COMMA_SPLITTER.splitToList(headerValue).stream())
                .findFirst();

        String s = stringStream.orElse(request.getRemoteAddr());

        return Optional.ofNullable(Strings.emptyToNull(s));
    }
}
