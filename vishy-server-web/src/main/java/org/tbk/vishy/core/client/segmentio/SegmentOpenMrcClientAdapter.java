package org.tbk.vishy.core.client.segmentio;

import com.github.segmentio.Client;
import com.github.segmentio.models.EventProperties;
import com.github.segmentio.models.Traits;
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.Map;

public class SegmentOpenMrcClientAdapter implements OpenMrcClient {

    private final Client analytics;

    public SegmentOpenMrcClientAdapter(Client analytics) {
        this.analytics = analytics;
    }

    @Override
    public String name() {
        return "segmentio";
    }

    @Override
    public void track(String userId, String event, Map<String, ?> properties) {

        boolean isIdentifyEvent = "identify".equals(event);
        if (isIdentifyEvent) {
            identifyEvent(userId, properties);
        } else {
            genericEvent(userId, event, properties);
        }
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
