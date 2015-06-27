package org.tbk.vishy.client.dropwizard;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;

/**
 * Created by void on 01.05.15.
 */
@Slf4j
public class DropwizardMetricsClientAdapter implements OpenMrcRequestConsumer {
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
    public void accept(OpenMrc.Request request) {
        this.vishyMetrics.incomingRequests.mark();

        log.info("received event for metrics {}", request.getType());

        if (request.getType() == OpenMrc.RequestType.SUMMARY) {
            onSummaryRequest(request);
        }

    }

    private void onSummaryRequest(OpenMrc.Request request) {
        OpenMrc.VisibilityTimeReport report = request.getSummary().getReport();
        this.vishyMetrics.timeVisible.update(report.getTimeVisible());
        this.vishyMetrics.timeHidden.update(report.getTimeHidden());
        this.vishyMetrics.timeFullyVisible.update(report.getTimeFullyVisible());
        this.vishyMetrics.timeTotal.update(report.getDuration());
    }

}
