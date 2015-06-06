package org.tbk.vishy;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        printBeanDefinitionNames(ctx);
        printEnvironmentVariables(ctx);
    }


    private static void printBeanDefinitionNames(ApplicationContext ctx) {
        Joiner comaJoiner = Joiner.on(", ");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        log.info("Beans in application context: {}", comaJoiner.join(beanNames));
    }

    private static void printEnvironmentVariables(ApplicationContext ctx) {
        ConfigurableEnvironment environment = ctx.getBean(ConfigurableEnvironment.class);

        Optional.ofNullable(environment)
                .map(ConfigurableEnvironment::getSystemProperties)
                .map(env -> env.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())))
                .ifPresent(env -> env.forEach(entry -> log.info("{} -> {}", entry.getKey(), entry.getValue())));

        Optional.ofNullable(environment)
                .map(ConfigurableEnvironment::getSystemEnvironment)
                .map(env -> env.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())))
                .ifPresent(env -> env.forEach(entry -> log.info("{} -> {}", entry.getKey(), entry.getValue())));
    }

}