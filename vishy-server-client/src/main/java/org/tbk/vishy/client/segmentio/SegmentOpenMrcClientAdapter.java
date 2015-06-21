package org.tbk.vishy.client.segmentio;

import com.github.segmentio.Client;
import com.github.segmentio.models.EventProperties;
import com.github.segmentio.models.Traits;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;

import java.util.Map;

public class SegmentOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final Client analytics;
    private final RequestToMapFunction mapper;

    public SegmentOpenMrcClientAdapter(Client analytics, RequestToMapFunction mapper) {
        this.analytics = analytics;
        this.mapper = mapper;
    }

    @Override
    public void accept(OpenMrc.Request request) {
        String userId = request.getMonitorId();
        Map<String, Object> properties = mapper.apply(request);

        if (isIdentifyEvent(request)) {
            identifyEvent(userId, properties);
        } else {
            genericEvent(userId, request.getType().name(), properties);
        }
    }

    private boolean isIdentifyEvent(OpenMrc.Request request) {
        return "IDENTIFY".equals(request.getType().name());
    }

    private void identifyEvent(String userId, Map<String, ?> properties) {
        Traits traits = new Traits();
        traits.putAll(properties);

        analytics.identify(userId, traits);
    }

    private void genericEvent(String userId, String event, Map<String, ?> properties) {
        EventProperties eventProperties = new EventProperties();
        eventProperties.putAll(properties);

        analytics.track(userId, event, eventProperties);
    }
}
