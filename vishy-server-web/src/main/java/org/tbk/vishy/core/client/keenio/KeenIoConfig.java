package org.tbk.vishy.core.client.keenio;

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
import org.tbk.openmrc.core.client.OpenMrcClient;

import java.util.Map;

@Configuration
@Conditional(KeenIoCondition.class)
public class KeenIoConfig {
    static final class EnvironmentVariables {
        private static final String PROJECT_ID = "KEENIO_PROJECT_ID";
        private static final String WRITE_KEY = "KEENIO_WRITE_KEY";
        private static final String READ_KEY = "KEENIO_READ_KEY";
    }

    private static final String DEFAULT_PROJECT_ID = "54dcbf0b46f9a74800890203";
    private static final String DEFAULT_WRITE_KEY = "3d4c9372032dad964bf03a00f579c78b9c410e34111b1929cf989c9eabaad11801bce63621b689f0ef4bbf7c84b47efe3fb94b27d125fc44fbd51d9f08c605b9e876c001d2105d1ba48f2fdee113b1b16e7e6847bb2b14822181262360cf668fab24ed77230e86dda8082be083b49a1e";
    private static final String DEFAULT_READ_KEY = "f0de436f2fb5f41ddc57d01f4933056645f15ed6f0a7f44a643eaa665129560e1a2ab21ff4a8e8a79926e1444d847b33cf7ce4cf49fe0e49fa7120cb34803e6066fbf26fda801986ae8a60ea01a715a07491fc516c5b723a41dfef512a173124a65c8f15889450d295d8bc5a71e73c7c";

    @Autowired
    private Environment environment;

    @Bean
    public OpenMrcClient keenOpenMrcClientAdapter(KeenClient keenClient) {
        return new KeenOpenMrcClientAdapter(keenClient);
    }

    @Bean
    public KeenClient keenClient() {
        KeenClient client = new JavaKeenClientBuilder().build();

        client.setDefaultProject(keenProject());

        client.setGlobalProperties(globalProperties());
        client.setGlobalPropertiesEvaluator(globalPropertiesEvaluator());

        client.setDebugMode(true);

        //MoreExecutors.addDelayedShutdownHook((ExecutorService) client.getPublishExecutor(), 10, TimeUnit.SECONDS);

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
        String projectId = environment.getProperty(EnvironmentVariables.PROJECT_ID, DEFAULT_PROJECT_ID);
        String writeKey = environment.getProperty(EnvironmentVariables.WRITE_KEY, DEFAULT_WRITE_KEY);
        String readKey = environment.getProperty(EnvironmentVariables.READ_KEY, DEFAULT_READ_KEY);

        return new KeenProject(projectId, writeKey, readKey);
    }

    private Map<String, Object> globalProperties() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("_vishy_server_id", "kepler273");
        return map;
    }
}
