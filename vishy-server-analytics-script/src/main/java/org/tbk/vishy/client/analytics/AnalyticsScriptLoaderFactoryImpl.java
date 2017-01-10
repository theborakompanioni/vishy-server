package org.tbk.vishy.client.analytics;

import com.google.common.base.CharMatcher;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.jsoup.safety.Whitelist;

import java.io.StringWriter;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class AnalyticsScriptLoaderFactoryImpl implements AnalyticsScriptLoaderFactory {
    private final Template template;
    private final VishyAnalyticsScriptProperties properties;
    private final Whitelist emptyWhitelist = Whitelist.none();

    private final CharMatcher lettersAndDigits = CharMatcher.javaLetterOrDigit().precomputed();

    public AnalyticsScriptLoaderFactoryImpl(VishyAnalyticsScriptProperties properties, Template template) {
        this.properties = requireNonNull(properties);
        this.template = requireNonNull(template);
    }

    @Override
    public String createLoaderScript(String projectId, String experimentId, String elementId) {
        checkArgument(lettersAndDigits.matchesAllOf(projectId));
        checkArgument(lettersAndDigits.matchesAllOf(experimentId));
        checkArgument(lettersAndDigits.matchesAllOf(elementId));

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
