package org.tbk.vishy.client.keenio;

import com.google.common.collect.Maps;
import io.keen.client.java.KeenCallback;
import io.keen.client.java.KeenClient;
import lombok.extern.slf4j.Slf4j;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.OpenMrcRequestToMapFunction;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

/**
 * Created by void on 01.05.15.
 */
@Slf4j
public class KeenOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final KeenClient keenClient;
    private final OpenMrcRequestToMapFunction toMapFunction;

    public KeenOpenMrcClientAdapter(KeenClient keenClient, OpenMrcRequestToMapFunction toMapFunction) {
        this.keenClient = keenClient;
        this.toMapFunction = toMapFunction;
    }

    @Override
    public void accept(OpenMrc.Request request) {
        Map<String, Object> event = Optional.ofNullable(request)
                .map(toMapFunction)
                .orElseThrow(IllegalArgumentException::new);

        Map<String, Object> cleanedEvent = replaceDotsInKeysWithColon(event);

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

    // keen does not like certain events
    // e.g. with "dots" in the key
    private <V> Map<String, V> replaceDotsInKeysWithColon(Map<String, V> event) {
        return newTransformedMap(event, Collectors.toMap(e -> e.getKey().replace(".", ":"), Entry::getValue));
    }

    private <K, V> Map<K, V> newTransformedMap(Map<K, V> target, Collector<Entry<K, V>, ?, Map<K, V>> collector) {
        Map<K, V> replacedKeysMap = Maps.newHashMapWithExpectedSize(target.size());

        replacedKeysMap.putAll(target.entrySet().stream()
                .collect(collector));

        return replacedKeysMap;
    }
}
