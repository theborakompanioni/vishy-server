package org.tbk.vishy;

import com.github.theborakompanioni.openmrc.OpenMrcRequestService;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;
import com.github.theborakompanioni.vishy.jdbc.OpenMrcJdbcSaveAction;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.vishy.verticle.HelloVerticle;
import org.tbk.vishy.web.OpenMrcRequestConsumerCtrl;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ComponentScan(value = {
        "org.tbk.vishy",
        "com.github.theborakompanioni"
})
@AutoConfigureAfter({VishySpringOpenMrcConfiguration.class})
@Configuration
public class VishyServerConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public HelloVerticle helloVerticle() {
        return new HelloVerticle(8081);
    }

    @Bean
    public RequestInterceptorConfiguration requestInterceptorConfiguration() {
        return new RequestInterceptorConfiguration();
    }

    @Bean
    public OpenMrcRequestConsumerCtrl openMrcRequestConsumerCtrl(
            OpenMrcRequestService<HttpServletRequest, ResponseEntity<String>> openMrcHttpRequestService) {
        return new OpenMrcRequestConsumerCtrl(openMrcHttpRequestService);
    }

    @Bean
    public OpenMrcJdbcSaveAction openMrcJdbcSaveAction(OpenMrcJsonMapper jsonMapper) {
        String TABLE_NAME = "vishy_openmrc_request";
        final String sql = "insert into " + TABLE_NAME + "(type, json) values (?,?)";

        return (jdbcTemplate1, request) -> {
            final String type = request.getType().name();
            final String json = jsonMapper.toJson(request);
            jdbcTemplate1.update(sql, type, json);
        };
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        /*int BROWSER_CACHE_CONTROL = 604800;
        registry
                .addResourceHandler("/vishy/scripts/**")
                .addResourceLocations("/vishy/scripts/")
                .setCachePeriod(BROWSER_CACHE_CONTROL);*/
    }
}
