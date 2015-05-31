package org.tbk.vishy.client.dropwizard;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.Map;

/**
 * Created by void on 01.05.15.
 */
@Slf4j
public class DropwizardMetricsClientAdapter implements OpenMrcClient {
    private static final String METRIC_PREFIX = "vishy";

    private class VishyMetrics {
        private final Meter incomingRequests;

        public VishyMetrics(MetricRegistry metricsRegistry) {
            this.incomingRequests = metricsRegistry.meter(MetricRegistry.name(METRIC_PREFIX, "requests.incoming"));
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
    }
}
