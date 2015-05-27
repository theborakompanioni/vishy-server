package org.tbk.vishy.client.segmentio;

import com.github.segmentio.Analytics;
import com.github.segmentio.Client;
import com.github.segmentio.Options;
import com.ning.http.client.AsyncHttpClientConfig;
import io.keen.client.java.KeenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.tbk.openmrc.core.client.OpenMrcClient;
import org.tbk.vishy.client.keenio.KeenOpenMrcClientAdapter;

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
    public OpenMrcClient keenOpenMrcClientAdapter(KeenClient keenClient) {
        return new KeenOpenMrcClientAdapter(keenClient);
    }

    @Bean
    public OpenMrcClient analyticsClient() {
        return new SegmentOpenMrcClientAdapter(analytics());
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
