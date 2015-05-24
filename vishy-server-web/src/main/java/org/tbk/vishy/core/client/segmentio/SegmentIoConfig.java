package org.tbk.vishy.core.client.segmentio;

import com.github.segmentio.Analytics;
import com.github.segmentio.Client;
import com.github.segmentio.Options;
import com.ning.http.client.AsyncHttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.client.OpenMrcClient;

@Configuration
public class SegmentIoConfig {
    private static String WRITE_KEY = "vkeLyauEBLwCnSj9Tf60u2KAsJ1xk8ra";

    @Bean
    public OpenMrcClient analyticsClient() {
        return new SegmentOpenMrcClientAdapter(analytics());
    }

    @Bean
    public Client analytics() {
        Analytics.initialize(WRITE_KEY, options());
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
