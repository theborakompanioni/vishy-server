package org.tbk.vishy.client.analytics;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "vishy.js.analytics.enabled", matchIfMissing = true)
@EnableConfigurationProperties(VishyAnalyticsScriptProperties.class)
public class VishyAnalyticsScriptConfig {

    /**
     * Reasons for static declaration: created very early in the application’s lifecycle
     * allows the bean to be created without having to instantiate the @Configuration class
     *
     * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-validation
     */
    @Bean
    public static VishyAnalyticsScriptPropertiesValidator configurationPropertiesValidator() {
        return new VishyAnalyticsScriptPropertiesValidator();
    }

    @Autowired
    private VishyAnalyticsScriptProperties properties;

    @Bean
    public VishyScriptLoaderCtrl vishyScriptLoaderCtrl(VelocityEngine velocityEngine) {
        return new VishyScriptLoaderCtrl(analyticsScriptLoaderFactory(velocityEngine));
    }

    @Bean
    public AnalyticsScriptLoaderFactory analyticsScriptLoaderFactory(VelocityEngine velocityEngine) {
        final Template template = velocityEngine.getTemplate("velocity/vishy_analytics_loader_template.vm");
        AnalyticsScriptLoaderFactory bean = new AnalyticsScriptLoaderFactoryImpl(properties, template);
        return bean;
    }
}
