package org.tbk.vishy.client.analytics;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Value;
import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Slf4j
@RestController
@RequestMapping("/openmrc")
public class VishyScriptLoaderCtrl {

    private static final String PUBLIC_PROJECT_ID = "DEMO";

    private AnalyticsScriptLoaderFactory scriptLoaderFactory;

    private final LoadingCache<CacheKey, String> scriptCache = CacheBuilder.newBuilder()
            .initialCapacity(100_000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(CacheLoader.from(key -> {
                return scriptLoaderFactory.createLoaderScript(key.getProjectId(), key.getElementId());
            }));

    @Value
    @Builder
    private static class CacheKey {
        private static int MAX_VALUE_LENGTH = 32;
        private static CharMatcher VALID_CHARS = CharMatcher.inRange('a', 'z')
                .or(CharMatcher.inRange('A', 'Z'))
                .or(CharMatcher.javaDigit())
                .or(CharMatcher.anyOf("-_"))
                .precomputed();

        private String elementId;
        private String projectId;
        private String experimentId;

        public boolean isValid() {
            return isValid(elementId) &&
                    isValid(projectId) &&
                    isValid(experimentId);
        }

        private boolean isValid(String value) {
            return !Strings.nullToEmpty(value).isEmpty() &&
                    value.length() < MAX_VALUE_LENGTH &&
                    VALID_CHARS.matchesAllOf(value);
        }
    }

    @Autowired
    public VishyScriptLoaderCtrl(final AnalyticsScriptLoaderFactory scriptLoaderFactory) {
        this.scriptLoaderFactory = requireNonNull(scriptLoaderFactory);
    }

    @RequestMapping(
            value = "/vishy/demo.js",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> demoProjectAnalyticsScript(@RequestParam(required = true) String elementId) {
        return A(PUBLIC_PROJECT_ID, elementId);
    }

    @RequestMapping(
            value = "/analytics/{projectId}/A",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> A(@PathVariable String projectId,
                               @RequestParam(required = true) String elementId) {
        return createResponse(projectId, elementId, "A");
    }

    @RequestMapping(
            value = "/analytics/{projectId}/B.js",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> B(@PathVariable String projectId,
                               @RequestParam(required = true) String elementId) {
        return createResponse(projectId, elementId, "B");
    }


    public ResponseEntity<?> createResponse(String projectId, String elementId, String experimentName) {
        try {
            final CacheKey cacheKey = CacheKey.builder()
                    .projectId(projectId)
                    .elementId(elementId)
                    .experimentId(experimentName)
                    .build();

            if (!cacheKey.isValid()) {
                log.warn("Invalid cacheKey provided: {}", cacheKey);
                return ResponseEntity.badRequest().build();
            }

            final String loaderScript = scriptCache.get(cacheKey);
            return ResponseEntity.ok(loaderScript);
        } catch (RuntimeException | Error e) {
            log.error("", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.warn("", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
