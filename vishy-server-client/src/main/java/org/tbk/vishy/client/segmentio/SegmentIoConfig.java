package org.tbk.vishy.client.segmentio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.segmentio.Analytics;
import com.github.segmentio.Client;
import com.github.segmentio.Options;
import com.ning.http.client.AsyncHttpClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;

import java.util.Objects;

@Configuration
@Conditional(SegmentIoCondition.class)
public class SegmentIoConfig {
    static final class EnvironmentVariables {
        private static final String WRITE_KEY = "SEGMENTIO_WRITE_KEY";
    }

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper mapper;

    @Bean
    public OpenMrcRequestConsumer analyticsClient() {
        return new SegmentOpenMrcClientAdapter(analytics(), RequestToMapFunction.create(mapper));
    }

    @Bean
    public Client analytics() {
        String writeKey = environment.getProperty(EnvironmentVariables.WRITE_KEY);

        Objects.requireNonNull(writeKey, "Environment variable " + EnvironmentVariables.WRITE_KEY + "not specified.");

        Analytics.initialize(writeKey, options());

        return Analytics.getDefaultClient();
    }

    @Bean
    public Options options() {
        return new Options()
                .setFlushAfter(5_000)
                .setMaxQueueSize(30_000)
                .setHttpConfig(asyncHttpClientConfig());
    }

    @Bean
    public AsyncHttpClientConfig asyncHttpClientConfig() {
        return new AsyncHttpClientConfig.Builder()
                .setMaximumConnectionsTotal(100)
                .setConnectionTimeoutInMs(3_000)
                .setMaxRequestRetry(3)
                .build();
    }
}
