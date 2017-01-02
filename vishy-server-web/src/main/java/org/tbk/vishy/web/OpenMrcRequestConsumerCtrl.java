package org.tbk.vishy.web;

import com.github.theborakompanioni.openmrc.spring.web.OpenMrcHttpRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/openmrc")
public class OpenMrcRequestConsumerCtrl {

    private final OpenMrcHttpRequestService openMrcService;

    @Autowired
    public OpenMrcRequestConsumerCtrl(OpenMrcHttpRequestService openMrcService) {
        this.openMrcService = openMrcService;
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "payload",
                    dataType = "String",
                    paramType = "body",
                    required = true
            )
    })
    public DeferredResult<HttpEntity<String>> consume(HttpServletRequest request) {
        final DeferredResult<HttpEntity<String>> deferredResult = new DeferredResult<>();
        openMrcService.apply(request)
                .subscribe(deferredResult::setResult,
                        e -> {
                            if (e instanceof RuntimeException) {
                                log.error("", e);
                                deferredResult.setErrorResult(new InternalServerException(e));
                            } else {
                                log.warn("", e);
                                deferredResult.setErrorResult(new BadRequestException(e));
                            }
                        });

        return deferredResult;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class InternalServerException extends Exception {
        public InternalServerException(Throwable t) {
            super(t);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends Exception {
        public BadRequestException(Throwable t) {
            super(t);
        }
    }
}
