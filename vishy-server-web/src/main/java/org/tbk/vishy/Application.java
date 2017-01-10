package org.tbk.vishy;

import com.google.common.collect.ImmutableList;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.context.ApplicationListener;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Slf4j
@SpringBootApplication(
        exclude = {
                // DataSource auto-config disabled to prevent spring
                // from erring "Cannot determine embedded database driver class"
                DataSourceAutoConfiguration.class,
                FlywayAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .listeners(applicationPidFileWriter(), embeddedServerPortFileWriter())
                .web(true)
                .run(args);
    }

    private static ApplicationListener<?> applicationPidFileWriter() {
        return new ApplicationPidFileWriter("app.pid");
    }

    private static ApplicationListener<?> embeddedServerPortFileWriter() {
        return new EmbeddedServerPortFileWriter("app.port");
    }

    private Vertx vertx;
    private List<Verticle> verticles;

    @Autowired
    public Application(Vertx vertx, List<Verticle> verticles) {
        this.vertx = requireNonNull(vertx);
        this.verticles = ImmutableList.copyOf(verticles);
    }

    @PostConstruct
    public void deployVerticle() {
        verticles.forEach(verticle -> vertx.deployVerticle(verticle));
    }
}
