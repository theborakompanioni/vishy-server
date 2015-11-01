package org.tbk.vishy.client.analytics;

/**
 * Created by void on 01.11.15.
 */
public interface AnalyticsScriptLoaderFactory {
    String createLoaderScript(String projectId, String elementId);
}
