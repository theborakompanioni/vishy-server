package org.tbk.vishy.client.keenio;

import io.keen.client.java.KeenClient;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;

import java.util.Map;

/**
 * Created by void on 01.05.15.
 */
public class KeenOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final KeenClient keenClient;
    private final RequestToMapFunction mapper;

    public KeenOpenMrcClientAdapter(KeenClient keenClient, RequestToMapFunction mapper) {
        this.keenClient = keenClient;
        this.mapper = mapper;
    }

    @Override
    public void accept(OpenMrc.Request request) {
        Map<String, Object> event = mapper.apply(request);

        keenClient.addEventAsync(request.getType().name(), event);
    }
}
