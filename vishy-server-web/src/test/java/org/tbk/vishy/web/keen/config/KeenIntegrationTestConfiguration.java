package org.tbk.vishy.web.keen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;
import org.tbk.vishy.client.keenio.KeenIoCondition;
import org.tbk.vishy.client.keenio.KeenIoConfig;
import org.tbk.vishy.client.keenio.KeenOpenMrcClientAdapter;

import java.util.function.Function;

@Configuration
@Conditional(KeenIoCondition.class)
public class KeenIntegrationTestConfiguration extends KeenIoConfig {
    @Bean
    public OpenMrcRequestConsumer keenOpenMrcClientAdapter() {
        Function<OpenMrc.Request, String> toEventNameFunction = request -> "test_" + request.getType().name();
        return new KeenOpenMrcClientAdapter(keenClient(), new RequestToMapFunction(), toEventNameFunction);
    }
}
