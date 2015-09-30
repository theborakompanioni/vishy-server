package org.tbk.vishy.client.dropwizard.influxdb;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import metrics_influxdb.InfluxdbHttp;
import metrics_influxdb.InfluxdbReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Slf4j
@Configuration
@ConditionalOnBean(MetricRegistry.class)
@ConditionalOnProperty("vishy.influxdb.enabled")
@EnableConfigurationProperties(InfluxdbProperties.class)
public class InfluxdbMetricsConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private InfluxdbProperties properties;

    private String host;
    private String port;

    @PostConstruct
    public void init() {
        String hostVariableName = requireNonNull(properties.getHostVariable());
        String portVariableName = requireNonNull(properties.getPortVariable());

        this.host = environment.getRequiredProperty(hostVariableName);
        this.port = environment.getRequiredProperty(portVariableName);
    }

    @Bean
    public InfluxdbHttp influxdbHttp() throws Exception {
        log.info("prepare influxdb reporter: {}:{}", host, port);

        InfluxdbHttp influxdbHttp = new InfluxdbHttp(
                host,
                Integer.valueOf(port),
                properties.getDatabase(),
                properties.getUsername(),
                properties.getPassword()
        );

        return influxdbHttp;
    }

    @Bean
    public InfluxdbReporter influxdbReporter(MetricRegistry metricRegistry, InfluxdbHttp influxdb) {
        InfluxdbReporter influxdbReporter = InfluxdbReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .skipIdleMetrics(properties.isSkipIdleMetrics())
                .build(influxdb);

        influxdbReporter.start(properties.getIntervalInSeconds(), TimeUnit.SECONDS);

        return influxdbReporter;
    }
}
