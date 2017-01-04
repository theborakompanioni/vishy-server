package org.tbk.vishy.filter;

import com.google.common.base.Stopwatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ResponseTimeHeaderFilter extends OncePerRequestFilter {

    private static final String RESPONSE_TIME_HEADER_NAME = "X-Response-Time";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        filterChain.doFilter(request, response);

        if (!response.isCommitted()) {
            response.addHeader(RESPONSE_TIME_HEADER_NAME, stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        }

        stopwatch.stop();
    }
}
