package org.tbk.vishy.client.analytics;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.StringWriter;

import static java.util.Objects.requireNonNull;

public class AnalyticsScriptLoaderFactoryImpl implements AnalyticsScriptLoaderFactory {
    final Template template;

    public AnalyticsScriptLoaderFactoryImpl(Template template) {
        this.template = requireNonNull(template);
    }

    @Override
    public String createLoaderScript(String projectId, String elementId) {
        String cleanProjectId = Jsoup.clean(projectId, Whitelist.none());
        String cleanElementId = Jsoup.clean(elementId, Whitelist.none());

        VelocityContext context = new VelocityContext();
        context.put("projectId", cleanProjectId);
        context.put("elementId", cleanElementId);
        context.put("analyticsScriptSrc", getAnalyticsScriptSource());
        context.put("protocol", "http");
        context.put("host", "localhost");
        context.put("port", "8080");

        StringWriter sw = new StringWriter();

        template.merge(context, sw);

        return sw.toString();
    }

    private String getAnalyticsScriptSource() {
        return "//localhost:8080/static/vishy-analytics/dist/vishy-analytics.min.js";
    }
}
