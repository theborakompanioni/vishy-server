package org.tbk.vishy.jdbc.config;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;
import com.github.theborakompanioni.vishy.jdbc.OpenMrcJdbcSaveAction;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.tbk.vishy.jdbc.converter.LocalDateTimeAttributeConverter;
import org.tbk.vishy.jdbc.model.AbstractEntity;
import org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequest;
import org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequestRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableConfigurationProperties(VishyJdbcProperties.class)
@ConditionalOnProperty(name = "vishy.model.jdbc.enabled", havingValue = "true")
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = AbstractEntity.class)
@EnableAsync
public class VishyJdbcConfig {
    /**
     * Reasons for static declaration: created very early in the application’s lifecycle
     * allows the bean to be created without having to instantiate the @Configuration class
     * <p>
     * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-validation
     */
    @Bean
    public static VishyJdbcPropertiesValidator configurationPropertiesValidator() {
        return new VishyJdbcPropertiesValidator();
    }

    @Autowired
    private VishyJdbcProperties properties;

    @Autowired(required = false)
    private MetricRegistry metricRegistry;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();

        properties.getDriverClassName()
                .ifPresent(config::setDriverClassName);

        config.setJdbcUrl(properties.getJdbcUrl());
        config.setUsername(properties.getUsername());
        config.setPassword(properties.getPassword());
        config.setPoolName("vishy-jdbc");

        if (metricRegistry != null) {
            config.setMetricRegistry(metricRegistry);
        }

        // TODO: make configurable
        config.addDataSourceProperty("cachePrepStmts", String.valueOf(true));
        config.addDataSourceProperty("prepStmtCacheSize", String.valueOf(250));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", String.valueOf(2048));

        return config;
    }

    @Bean
    public HikariDataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public JdbcTemplate vishyJdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

    @Bean
    public OpenMrcJdbcSaveAction openMrcJdbcSaveAction(
            PersistedOpenMrcRequestRepository requestRepository, OpenMrcJsonMapper jsonMapper
    ) {
        return (jdbcTemplate1, request) -> {
            final PersistedOpenMrcRequest dbEntity = PersistedOpenMrcRequest
                    .create(request)
                    .build();

            requestRepository.save(dbEntity);
        };
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //vendorAdapter.setDatabase(Database.HSQL);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
                AbstractEntity.class.getPackage().getName(),
                LocalDateTimeAttributeConverter.class.getPackage().getName()
        );
        factory.setDataSource(hikariDataSource());

        return factory;
    }

    @PostConstruct
    public void postConstruct() {
        if (properties.isTableSetupEnabled()) {
            Flyway flyway = new Flyway();
            flyway.setLocations("db/model/migration");
            flyway.setTable("vishy_model_schema_version");
            flyway.setDataSource(hikariDataSource());

            log.info("Starting flyway migration v{}", flyway.getBaselineVersion().getVersion());

            flyway.migrate();
        }

    }
}
