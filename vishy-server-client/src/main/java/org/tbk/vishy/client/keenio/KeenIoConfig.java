package org.tbk.vishy.client.keenio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.keen.client.java.GlobalPropertiesEvaluator;
import io.keen.client.java.JavaKeenClientBuilder;
import io.keen.client.java.KeenClient;
import io.keen.client.java.KeenProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;

import java.util.Map;
import java.util.Objects;

@Configuration
@Conditional(KeenIoCondition.class)
public class KeenIoConfig {
    static final class EnvironmentVariables {
        private static final String PROJECT_ID = "KEENIO_PROJECT_ID";
        private static final String WRITE_KEY = "KEENIO_WRITE_KEY";
        private static final String READ_KEY = "KEENIO_READ_KEY";
    }

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper mapper;

    @Bean
    public OpenMrcRequestConsumer keenOpenMrcClientAdapter() {
        return new KeenOpenMrcClientAdapter(keenClient(), RequestToMapFunction.create(mapper));
    }

    @Bean
    public KeenClient keenClient() {
        KeenClient client = new JavaKeenClientBuilder().build();

        client.setDefaultProject(keenProject());

        client.setGlobalProperties(globalProperties());
        client.setGlobalPropertiesEvaluator(globalPropertiesEvaluator());

        client.setDebugMode(true);

        return client;
    }

    @Bean
    public GlobalPropertiesEvaluator globalPropertiesEvaluator() {
        return s -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("_vishy_container_id", "docker" + (Math.random() * 10) % 10);
            return map;
        };
    }

    @Bean
    public KeenProject keenProject() {
        String projectId = environment.getProperty(EnvironmentVariables.PROJECT_ID);
        String writeKey = environment.getProperty(EnvironmentVariables.WRITE_KEY);
        String readKey = environment.getProperty(EnvironmentVariables.READ_KEY);

        Objects.requireNonNull(projectId, "Environment variable " + EnvironmentVariables.PROJECT_ID + "not specified.");
        Objects.requireNonNull(writeKey, "Environment variable " + EnvironmentVariables.WRITE_KEY + "not specified.");
        Objects.requireNonNull(readKey, "Environment variable " + EnvironmentVariables.READ_KEY + "not specified.");

        return new KeenProject(projectId, writeKey, readKey);
    }

    private Map<String, Object> globalProperties() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("_vishy_server_id", "kepler273");
        return map;
    }
}
