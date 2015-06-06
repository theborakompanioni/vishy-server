package org.tbk.vishy.client.dropwizard.influxdb;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import metrics_influxdb.InfluxdbHttp;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.InfluxdbUdp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.tbk.vishy.client.dropwizard.DropwizardMetricsConfig;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@Conditional({DropwizardMetricsConfig.DropwizardMetricsCondition.class, InfluxdbMetricsConfig.InfluxdbMetricsCondition.class})
public class InfluxdbMetricsConfig {
    public static class InfluxdbMetricsCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !Strings.nullToEmpty(context.getEnvironment().getProperty(EnvironmentVariables.INFLUXDB_HOST_VAR)).isEmpty();
        }
    }

    final class EnvironmentVariables {
        private static final String INFLUXDB_HOST_VAR = "INFLUXDB_HOST_VAR";
        private static final String INFLUXDB_PORT_VAR = "INFLUXDB_PORT_VAR";
        private static final String INFLUXDB_NAME = "INFLUXDB_NAME";
        private static final String INFLUXDB_USER = "INFLUXDB_USER";
        private static final String INFLUXDB_PASS = "INFLUXDB_PASS";

        String host;
        String port;
        String database;
        String username;
        String password;

        public EnvironmentVariables(Environment environment) {
            String hostVariableName = environment.getProperty(EnvironmentVariables.INFLUXDB_HOST_VAR);
            String portVariableName = environment.getProperty(EnvironmentVariables.INFLUXDB_PORT_VAR);

            this.host = environment.getRequiredProperty(hostVariableName);
            this.port = environment.getRequiredProperty(portVariableName);
            this.database = environment.getProperty(EnvironmentVariables.INFLUXDB_NAME);
            this.username = environment.getProperty(EnvironmentVariables.INFLUXDB_USER);
            this.password = environment.getProperty(EnvironmentVariables.INFLUXDB_PASS);
        }

        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("host", host)
                    .add("port", port)
                    .add("database", database)
                    .add("username", username)
                    .add("password", password)
                    .toString();
        }
    }

    @Autowired
    private Environment environment;

    @Bean
    public EnvironmentVariables environmentVariables() {
        return new EnvironmentVariables(environment);
    }

    @Bean
    public InfluxdbHttp influxdbHttp(EnvironmentVariables config) throws Exception {
        log.info("prepare influxdb reporter: {}", config);

        InfluxdbHttp influxdbHttp = new InfluxdbHttp(
                config.host,
                Integer.valueOf(config.port),
                config.database,
                config.username,
                config.password
        );

        return influxdbHttp;
    }

    @Bean
    public InfluxdbReporter influxdbReporter(MetricRegistry metricRegistry, InfluxdbHttp influxdb) {
        InfluxdbReporter influxdbReporter = InfluxdbReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .skipIdleMetrics(false)
                .build(influxdb);

        influxdbReporter.start(10, TimeUnit.SECONDS);

        return influxdbReporter;
    }
}
