package org.tbk.vishy.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public final class HttpServletRequestUtils {
    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return Optional.ofNullable(request);
        }

        if (log.isDebugEnabled()) {
            log.debug("Not called in the context of an HTTP request");
        }

        return Optional.empty();
    }
}
