package org.tbk.vishy.client.kafka;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.googlecode.protobuf.format.JsonFormat;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaOpenMrcClientAdapter implements OpenMrcRequestConsumer {

    private final Producer<String, String> client;

    public KafkaOpenMrcClientAdapter(Producer<String, String> client) {
        this.client = client;
    }

    @Override
    public void accept(OpenMrc.Request request) {
        String json = JsonFormat.printToString(request);
        String topic = request.getType().name();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, json);

        client.send(producerRecord);
    }
}
