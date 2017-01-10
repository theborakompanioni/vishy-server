package org.tbk.vishy.client.analytics;

import com.google.common.base.CharMatcher;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class AnalyticsScriptLoaderFactoryImpl implements AnalyticsScriptLoaderFactory {
    private final Template template;
    private final VishyAnalyticsScriptProperties properties;

    private final CharMatcher lettersAndDigitsMatcher = CharMatcher.javaLetterOrDigit().precomputed();
    private final CharMatcher validElementIdMatcher = CharMatcher.anyOf("_-")
            .or(lettersAndDigitsMatcher)
            .precomputed();

    public AnalyticsScriptLoaderFactoryImpl(VishyAnalyticsScriptProperties properties, Template template) {
        this.properties = requireNonNull(properties);
        this.template = requireNonNull(template);
    }

    @Override
    public String createLoaderScript(String projectId, String experimentId, String elementId) {
        checkArgument(lettersAndDigitsMatcher.matchesAllOf(projectId));
        checkArgument(lettersAndDigitsMatcher.matchesAllOf(experimentId));
        checkArgument(validElementIdMatcher.matchesAllOf(elementId));

        VelocityContext context = new VelocityContext();
        context.put("projectId", projectId);
        context.put("experimentId", experimentId);
        context.put("elementId", elementId);
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
