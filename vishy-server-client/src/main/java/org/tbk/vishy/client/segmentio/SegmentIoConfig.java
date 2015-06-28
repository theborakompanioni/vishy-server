package org.tbk.vishy.client.segmentio;

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
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;
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

    @Bean
    public OpenMrcRequestConsumer analyticsClient(StandardOpenMrcJsonMapper standardOpenMrcJsonMapper) {
        return new SegmentOpenMrcClientAdapter(analytics(), new RequestToMapFunction());
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
