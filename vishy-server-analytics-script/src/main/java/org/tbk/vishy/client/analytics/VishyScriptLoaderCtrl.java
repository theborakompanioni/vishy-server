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

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Slf4j
@RestController
@RequestMapping("/openmrc")
public class VishyScriptLoaderCtrl {
    private static final String PUBLIC_PROJECT_ID = "1";
    private static final String PUBLIC_EXPERIMENT_ID1 = "1";
    private static final String PUBLIC_EXPERIMENT_ID2 = "2";

    private AnalyticsScriptLoaderFactory scriptLoaderFactory;

    private final LoadingCache<CacheKey, String> scriptCache = CacheBuilder.newBuilder()
            .initialCapacity(100_000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(CacheLoader.from(key -> {
                return scriptLoaderFactory.createLoaderScript(
                        key.getProjectId(),
                        key.getExperimentId(),
                        key.getElementId());
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
        final boolean whatShouldIChoose = ThreadLocalRandom.current().nextFloat() > 0.5f;
        return whatShouldIChoose ?
                A(PUBLIC_PROJECT_ID, PUBLIC_EXPERIMENT_ID1, elementId) :
                B(PUBLIC_PROJECT_ID, PUBLIC_EXPERIMENT_ID2, elementId);
    }

    @RequestMapping(
            value = "/analytics/{projectId}/{experimentId}/A.js",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> A(@PathVariable String projectId, @PathVariable String experimentId,
                               @RequestParam(required = true) String elementId) {
        return createResponse(projectId, experimentId, elementId);
    }

    @RequestMapping(
            value = "/analytics/{projectId}/{experimentId}/B.js",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> B(@PathVariable String projectId, @PathVariable String experimentId,
                               @RequestParam(required = true) String elementId) {
        return createResponse(projectId, "2", elementId);
    }


    public ResponseEntity<?> createResponse(String projectId, String experimentId, String elementId) {
        try {
            final CacheKey cacheKey = CacheKey.builder()
                    .projectId(projectId)
                    .elementId(elementId)
                    .experimentId(experimentId)
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
