package org.tbk.vishy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(jacksonJdk8Module());

        return objectMapper;
    }

    @Bean
    public Jdk8Module jacksonJdk8Module() {
        return new Jdk8Module();
    }
}
