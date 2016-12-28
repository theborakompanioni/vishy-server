package org.tbk.vishy.client.analytics;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.StringWriter;

import static java.util.Objects.requireNonNull;

public class AnalyticsScriptLoaderFactoryImpl implements AnalyticsScriptLoaderFactory {
    private final Template template;
    private final VishyAnalyticsScriptProperties properties;
    private final Whitelist emptyWhitelist = Whitelist.none();

    public AnalyticsScriptLoaderFactoryImpl(VishyAnalyticsScriptProperties properties, Template template) {
        this.properties = requireNonNull(properties);
        this.template = requireNonNull(template);
    }

    @Override
    public String createLoaderScript(String projectId, String elementId) {
        String cleanProjectId = Jsoup.clean(projectId, emptyWhitelist);
        String cleanElementId = Jsoup.clean(elementId, emptyWhitelist);

        VelocityContext context = new VelocityContext();
        context.put("projectId", cleanProjectId);
        context.put("elementId", cleanElementId);
        context.put("analyticsScriptSrc", getAnalyticsScriptSource());
        context.put("protocol", properties.getProtocol());
        context.put("host", properties.getHost());
        context.put("port", properties.getPort());

        StringWriter sw = new StringWriter();

        template.merge(context, sw);

        return sw.toString();
    }

    private String getAnalyticsScriptSource() {
        return "//" + properties.getHost() + ":" + properties.getPort() + "/" + properties.getScriptSrc();
    }
}
