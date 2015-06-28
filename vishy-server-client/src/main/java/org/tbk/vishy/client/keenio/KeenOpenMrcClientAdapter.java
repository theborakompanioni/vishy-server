package org.tbk.vishy.client.keenio;

import com.google.common.collect.Maps;
import io.keen.client.java.KeenCallback;
import io.keen.client.java.KeenClient;
import lombok.extern.slf4j.Slf4j;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.OpenMrcRequestToMapFunction;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

/**
 * Created by void on 01.05.15.
 */
@Slf4j
public class KeenOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final KeenClient keenClient;
    private final OpenMrcRequestToMapFunction mapper;

    public KeenOpenMrcClientAdapter(KeenClient keenClient, OpenMrcRequestToMapFunction mapper) {
        this.keenClient = keenClient;
        this.mapper = mapper;
    }

    @Override
    public void accept(OpenMrc.Request request) {
        Map<String, Object> event = mapper.apply(request);

        Map<String, Object> cleanedEvent = replaceDotsInKeysWithUnderscore(event);

        log.info("received event for keenio {}", request.getType());

        keenClient.addEvent(null, request.getType().name(), cleanedEvent, null, new KeenCallback() {
            @Override
            public void onSuccess() {
                log.debug("successfully sent keenio event: {}", cleanedEvent);
            }

            @Override
            public void onFailure(Exception e) {
                log.warn("", e);
            }
        });
    }

    private Map<String, Object> replaceDotsInKeysWithUnderscore(Map<String, Object> event) {
        return replaceDotsInKeysWith(event, "_");
    }

    // keen does not like certain events
    // e.g. with "dots" in the key
    private Map<String, Object> replaceDotsInKeysWith(Map<String, Object> event, String replacement) {
        Map<String, Object> replacedKeysMap = Maps.newHashMapWithExpectedSize(event.size());

        replacedKeysMap.putAll(event.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().replace(".", replacement), Entry::getValue)));

        return replacedKeysMap;
    }
}
