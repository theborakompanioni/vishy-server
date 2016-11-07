package org.tbk.vishy.client.analytics;

public interface AnalyticsScriptLoaderFactory {
    String createLoaderScript(String projectId, String elementId);
}
