package org.tbk.vishy.core.client.keenio;

import io.keen.client.java.KeenClient;
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.Map;

/**
 * Created by void on 01.05.15.
 */
public class KeenOpenMrcClientAdapter implements OpenMrcClient {

    private final KeenClient keenClient;

    public KeenOpenMrcClientAdapter(KeenClient keenClient) {
        this.keenClient = keenClient;
    }

    @Override
    public String name() {
        return "keenio";
    }

    @Override
    public void track(String userId, String event, Map<String, ?> properties) {
        keenClient.addEventAsync(event, (Map<String, Object>) properties);
    }
}
