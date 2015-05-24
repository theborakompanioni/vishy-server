package org.tbk.vishy.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by void on 11.03.15.
 */
@Configuration
public class CorsConfiguration {
    @Bean
    public SimpleCorsFilter simpleCorsFilter() {
        return new SimpleCorsFilter();
    }
}
