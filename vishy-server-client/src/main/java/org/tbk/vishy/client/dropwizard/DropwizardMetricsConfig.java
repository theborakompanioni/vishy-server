package org.tbk.vishy.client.dropwizard;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@Conditional(DropwizardMetricsConfig.DropwizardMetricsCondition.class)
public class DropwizardMetricsConfig {
    public static class DropwizardMetricsCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !Strings.nullToEmpty(context.getEnvironment().getProperty("VISHY_ENABLE_METRICS")).equalsIgnoreCase("false");
        }
    }

    @Bean
    public OpenMrcClient dropwizardMetricsOpenMrcClientAdapter(MetricRegistry metricRegistry) {
        return new DropwizardMetricsClientAdapter(metricRegistry);
    }

    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry metricRegistry = new MetricRegistry();

        return metricRegistry;
    }

    @Bean
    public ConsoleReporter consoleReporter(MetricRegistry metricRegistry) {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();

        reporter.start(10, TimeUnit.SECONDS);

        return reporter;
    }
}
