package org.tbk.vishy.config.cors;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import lombok.Value;
import lombok.experimental.Builder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;


public class SimpleCorsFilter implements Filter {
    @Value
    @Builder
    public static class SimpleCorsFilterConfig {
        private static final Collection<String> DEFAULT_ALLOWED_METHODS = Arrays
                .asList("POST", "GET", "OPTIONS", "DELETE");
        private static final Collection<String> DEFAULT_ALLOWED_HEADERS = Arrays
                .asList("accept", "content-type", "x-requested-with");
        private static final int DEFAULT_MAX_AGE = 3600;
        private static final boolean DEFAULT_ALLOW_CREDENTIALS = true;

        public static SimpleCorsFilterConfig createDefault() {
            return SimpleCorsFilterConfig.builder()
                    .allowCredentials(DEFAULT_ALLOW_CREDENTIALS)
                    .allowedHeaders(DEFAULT_ALLOWED_HEADERS)
                    .allowedMethods(DEFAULT_ALLOWED_METHODS)
                    .maxAge(DEFAULT_MAX_AGE)
                    .build();
        }

        private Collection<String> allowedMethods;
        private Collection<String> allowedHeaders;
        private int maxAge;
        private boolean allowCredentials;
    }

    private static final CharMatcher BREAKING_MATCHER = CharMatcher.anyOf("\r\n");

    private final String allowedMethods;
    private final String allowedHeaders;
    private final String maxAge;
    private final String allowCredentials;

    public SimpleCorsFilter() {
        this(SimpleCorsFilterConfig.createDefault());
    }

    public SimpleCorsFilter(final SimpleCorsFilterConfig config) {
        requireNonNull(config);
        checkArgument(config.getMaxAge() >= 0);

        this.allowedMethods = requireNonNull(config.getAllowedMethods()).stream()
                .collect(joining(","));
        this.allowedHeaders = requireNonNull(config.getAllowedHeaders()).stream()
                .collect(joining(","));
        this.maxAge = String.valueOf(config.getMaxAge());
        this.allowCredentials = String.valueOf(config.isAllowCredentials());
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Optional<String> originHeaderValue = Optional.ofNullable(request.getHeader(HttpHeaders.ORIGIN))
                .map(Strings::emptyToNull)
                .map(BREAKING_MATCHER::removeFrom);

        final String allowOrigin = originHeaderValue.orElse("*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, this.allowedHeaders);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, this.maxAge);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, this.allowedHeaders);

        originHeaderValue.ifPresent(foo -> response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, allowCredentials));

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
