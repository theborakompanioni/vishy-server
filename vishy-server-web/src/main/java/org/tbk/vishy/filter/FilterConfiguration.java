package org.tbk.vishy.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public SimpleCorsFilter simpleCorsFilter() {
        return new SimpleCorsFilter();
    }

    @Bean
    public PoweredByHeaderFilter poweredByHeaderFilter() {
        return new PoweredByHeaderFilter("openmrc");
    }

    @Bean
    public ResponseTimeHeaderFilter responseTimeHeaderFilter() {
        return new ResponseTimeHeaderFilter();
    }
}
