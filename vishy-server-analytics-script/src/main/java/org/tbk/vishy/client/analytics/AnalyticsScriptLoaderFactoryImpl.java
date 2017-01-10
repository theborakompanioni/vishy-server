package org.tbk.vishy.client.analytics;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class AnalyticsScriptLoaderFactoryImpl implements AnalyticsScriptLoaderFactory {
    private final Template template;
    private final VishyAnalyticsScriptProperties properties;


    public AnalyticsScriptLoaderFactoryImpl(VishyAnalyticsScriptProperties properties, Template template) {
        this.properties = requireNonNull(properties);
        this.template = requireNonNull(template);
    }

    @Override
    public String createLoaderScript(ScriptConfig scriptConfig) {
        checkArgument(scriptConfig.isValid(), "Invalid script configuration.");

        VelocityContext context = new VelocityContext();
        context.put("projectId", scriptConfig.getProjectId());
        context.put("experimentId", scriptConfig.getExperimentId());
        context.put("elementId", scriptConfig.getElementId());
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
