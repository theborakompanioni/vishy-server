package org.tbk.vishy.web;

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
import org.tbk.vishy.client.analytics.AnalyticsScriptLoaderFactory;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/openmrc")
public class VishyScriptLoaderCtrl {

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
        private String elementId;
        private String projectId;
        private String experimentId;
    }

    @Autowired
    public VishyScriptLoaderCtrl(AnalyticsScriptLoaderFactory scriptLoaderFactory) {
        this.scriptLoaderFactory = scriptLoaderFactory;
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
            final String loaderScript = scriptCache.get(CacheKey.builder()
                    .projectId(projectId)
                    .elementId(elementId)
                    .experimentId(experimentName)
                    .build());
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
