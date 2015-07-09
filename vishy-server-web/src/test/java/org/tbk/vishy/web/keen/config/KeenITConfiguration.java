package org.tbk.vishy.web.keen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import org.tbk.vishy.client.RequestToMapFunction;
import org.tbk.vishy.client.keenio.KeenIoCondition;
import org.tbk.vishy.client.keenio.KeenIoConfig;
import org.tbk.vishy.client.keenio.KeenOpenMrcClientAdapter;

import java.util.function.Function;

@Configuration
@Conditional(KeenIoCondition.class)
public class KeenITConfiguration extends KeenIoConfig {
    @Bean
    public OpenMrcRequestConsumer keenOpenMrcClientAdapter() {
        String testEventPrefix = "test_";
        Function<OpenMrc.Request, String> toEventNameFunction = request -> testEventPrefix + request.getType().name();
        return new KeenOpenMrcClientAdapter(keenClient(), new RequestToMapFunction(), toEventNameFunction);
    }
}
