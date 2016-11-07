package org.tbk.vishy.client.kafka;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import static java.util.Objects.requireNonNull;

@Slf4j
public class KafkaOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final Producer<String, String> client;

    public KafkaOpenMrcClientAdapter(Producer<String, String> client) {
        this.client = requireNonNull(client);
    }

    @Override
    public void accept(OpenMrc.Request request) {
        String json = JsonFormat.printToString(request);
        String topic = request.getType().name();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, json);

        logDebugMessageIfEnabled(json);

        client.send(producerRecord);
    }

    private static void logDebugMessageIfEnabled(String json) {
        if (log.isDebugEnabled()) {
            log.debug("Sending request to kafka: {}", json);
        }
    }
}
