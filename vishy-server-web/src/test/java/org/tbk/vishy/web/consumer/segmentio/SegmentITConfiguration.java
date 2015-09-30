package org.tbk.vishy.web.consumer.segmentio;

import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.tbk.vishy.client.RequestToMapFunction;
import org.tbk.vishy.client.segmentio.SegmentIoConfig;
import org.tbk.vishy.client.segmentio.SegmentIoProperties;
import org.tbk.vishy.client.segmentio.SegmentOpenMrcClientAdapter;

import static org.mockito.Mockito.spy;

@Configuration
@EnableConfigurationProperties(SegmentIoProperties.class)
@ConditionalOnProperty("vishy.segmentio.enabled")
class SegmentITConfiguration extends SegmentIoConfig {

    @Bean
    @Primary
    public OpenMrcRequestConsumer analyticsClient() {
        final SegmentOpenMrcClientAdapter client = new SegmentOpenMrcClientAdapter(analytics(), new RequestToMapFunction());

        final SegmentOpenMrcClientAdapter clientSpy = spy(client);

        return clientSpy;
    }
}
