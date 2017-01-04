package org.tbk.vishy.filter;

import com.google.common.base.Strings;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class PoweredByHeaderFilter extends OncePerRequestFilter {

    private static final String POWERED_BY_HEADER_NAME = "X-Powered-By";
    private final String headerValue;

    public PoweredByHeaderFilter(String headerValue) {
        this.headerValue = requireNonNull(Strings.emptyToNull(headerValue));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader(POWERED_BY_HEADER_NAME, headerValue);

        filterChain.doFilter(request, response);
    }

}
