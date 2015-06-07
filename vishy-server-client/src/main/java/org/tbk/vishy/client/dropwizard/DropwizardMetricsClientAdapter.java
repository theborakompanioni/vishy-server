package org.tbk.vishy.client.dropwizard;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.Map;
import java.util.Optional;

/**
 * Created by void on 01.05.15.
 */
@Slf4j
public class DropwizardMetricsClientAdapter implements OpenMrcClient {
    private static final String METRIC_PREFIX = "vishy";

    private class VishyMetrics {
        private final Meter incomingRequests;
        private final Histogram timeVisible;
        private final Histogram timeFullyVisible;
        private final Histogram timeHidden;
        private final Histogram timeTotal;

        public VishyMetrics(MetricRegistry metricsRegistry) {
            this.incomingRequests = metricsRegistry.meter(MetricRegistry.name(METRIC_PREFIX, "requests.incoming"));

            this.timeVisible = metricsRegistry.histogram(MetricRegistry.name(METRIC_PREFIX, "time.visible"));
            this.timeFullyVisible = metricsRegistry.histogram(MetricRegistry.name(METRIC_PREFIX, "time.fullyvisible"));
            this.timeHidden = metricsRegistry.histogram(MetricRegistry.name(METRIC_PREFIX, "time.hidden"));
            this.timeTotal = metricsRegistry.histogram(MetricRegistry.name(METRIC_PREFIX, "time.total"));
        }
    }

    private final VishyMetrics vishyMetrics;

    public DropwizardMetricsClientAdapter(MetricRegistry metricsRegistry) {
        this.vishyMetrics = new VishyMetrics(metricsRegistry);
    }

    @Override
    public String name() {
        return "metrics";
    }

    @Override
    public void track(String userId, String event, Map<String, ?> properties) {
        log.info("received event for influxdb {}", event);
        this.vishyMetrics.incomingRequests.mark();
        if ("summary".equals(properties.get("type"))) {
            Map<String, ?> report = (Map<String, ?>) properties.get("report");
            if(report != null) {
                onSummaryRequest(report);
            }
        }

    }

    private void onSummaryRequest(Map<String, ?> report) {
        long timeVisible = Optional.ofNullable((Long) report.get("timeVisible")).orElse(0L);
        long timeHidden = Optional.ofNullable((Long) report.get("timeHidden")).orElse(0L);
        long timeFullyVisible = Optional.ofNullable((Long) report.get("timeFullyVisible")).orElse(0L);
        long duration = Optional.ofNullable((Long) report.get("duration")).orElse(0L);

        this.vishyMetrics.timeVisible.update(timeVisible);
        this.vishyMetrics.timeHidden.update(timeHidden);
        this.vishyMetrics.timeFullyVisible.update(timeFullyVisible);
        this.vishyMetrics.timeTotal.update(duration);
    }

}
