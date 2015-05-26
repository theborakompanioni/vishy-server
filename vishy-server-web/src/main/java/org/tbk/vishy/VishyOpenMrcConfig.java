package org.tbk.vishy;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tbk.openmrc.core.OpenMrcConfig;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.OpenMrcService;
import org.tbk.openmrc.core.client.OpenMrcClient;
import org.tbk.openmrc.core.client.OpenMrcClientList;
import org.tbk.openmrc.core.properties.evaluator.PropertyExtractionAlgorithm;
import org.tbk.openmrc.core.properties.evaluator.PropertyExtractionAlgorithmFactory;
import org.tbk.openmrc.core.properties.evaluator.PropertyProviderFactoryExtractionAlgorithm;
import org.tbk.openmrc.core.properties.provider.GenericPropertyProviderFactory;
import org.tbk.openmrc.core.properties.provider.PropertyProviderFactory;
import org.tbk.openmrc.simple.SimpleOpenMrcService;
import org.tbk.spring.useragentutils.UserAgentUtils;
import org.tbk.vishy.core.properties.provider.*;
import org.tbk.vishy.model.geolocation.GeoLocation;
import org.tbk.vishy.model.geolocation.GeoLocationUtils;
import org.tbk.vishy.web.VishyOpenMrcServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class VishyOpenMrcConfig extends WebMvcConfigurerAdapter {

    private Function<HttpServletRequest, Supplier<Optional<UserAgent>>> extractUserAgentFromHttpRequest =
            request -> (Supplier<Optional<UserAgent>>) () -> Optional.ofNullable(UserAgentUtils.getCurrentUserAgent(request));

    private Function<HttpServletRequest, Supplier<Optional<GeoLocation>>> extractGeoLocationFromHttpRequest =
            request -> (Supplier<Optional<GeoLocation>>) () -> GeoLocationUtils.getCurrentGeoLocation(request);

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public OpenMrcConfig openMrcConfig(OpenMrcClientList clientList,
                                       PropertyExtractionAlgorithmFactory propertyExtractionAlgorithmFactory,
                                       OpenMrcService openMrcService) {
        return new OpenMrcConfig() {
            @Override
            public OpenMrcClientList clientList() {
                return clientList;
            }

            @Override
            public PropertyExtractionAlgorithmFactory propertyEvaluatorFactory() {
                return propertyExtractionAlgorithmFactory;
            }

            @Override
            public OpenMrcService service() {
                return openMrcService;
            }
        };
    }

    @Bean
    public PropertyExtractionAlgorithmFactory propertyEvaluatorFactory(PropertyExtractionAlgorithm propertyEvaluator) {
        final Optional<PropertyExtractionAlgorithm> optional = Optional.ofNullable(propertyEvaluator);
        return eventName -> optional;
    }

    @Bean
    public PropertyExtractionAlgorithm propertyEvaluator(List<PropertyProviderFactory> propertyProviderFactories) {
        return new PropertyProviderFactoryExtractionAlgorithm(propertyProviderFactories);
    }

    @Bean
    public OpenMrcService openMrcService(PropertyExtractionAlgorithmFactory propertyEvaluatorFactory, OpenMrcClientList clientList) {
        return new SimpleOpenMrcService(propertyEvaluatorFactory, clientList);
    }

    @Bean
    public OpenMrcClient loggingOpenMrcClient() {
        return new OpenMrcClient() {
            @Override
            public String name() {
                return "logging";
            }

            @Override
            public void track(String userId, String event, Map<String, ?> properties) {
                log.info("track {}: {} - {}", userId, event, properties);
            }
        };
    }

    @Bean
    public OpenMrcClientList vishyOpenMrcClientList(List<OpenMrcClient> clients) {
        return new VishyOpenMrcClientList(clients);
    }

    @Bean
    public PropertyProviderFactory localePropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(HttpServletRequest::getLocale)
                                .map(LocalePropertiesProvider::new)
                                .flatMap(LocalePropertiesProvider::get));
    }

    @Bean
    public PropertyProviderFactory browserPropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(extractUserAgentFromHttpRequest)
                                .map(BrowserPropertiesProvider::new)
                                .flatMap(BrowserPropertiesProvider::get));
    }

    @Bean
    public PropertyProviderFactory operatingSystemPropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(extractUserAgentFromHttpRequest)
                                .flatMap(Supplier::get)
                                .map(UserAgent::getOperatingSystem)
                                .map(OperatingSystemPropertiesProvider::new)
                                .flatMap(OperatingSystemPropertiesProvider::get));
    }

    @Bean
    public PropertyProviderFactory geoLocationPropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) ->
                        () -> Optional.ofNullable(requestOrNull)
                                .flatMap(OpenMrcRequestContext::httpRequest)
                                .map(extractGeoLocationFromHttpRequest)
                                .flatMap(Supplier::get)
                                .map(GeoLocationPropertiesProvider::new)
                                .flatMap(GeoLocationPropertiesProvider::get));
    }

    @Bean
    public PropertyProviderFactory devicePropertyProviderFactory() {
        return new GenericPropertyProviderFactory(
                (OpenMrcRequestContext requestOrNull) -> {
                    Optional<HttpServletRequest> request = Optional.ofNullable(requestOrNull)
                            .flatMap(OpenMrcRequestContext::httpRequest);
                    Device deviceOrNull = request.map(DeviceUtils::getCurrentDevice).orElse(null);

                    return () -> request
                            .map(extractUserAgentFromHttpRequest)
                            .flatMap(Supplier::get)
                            .map(UserAgent::getOperatingSystem)
                            .map(OperatingSystem::getDeviceType)
                            .map(deviceType -> new DevicePropertiesProvider(deviceType, deviceOrNull))
                            .flatMap(DevicePropertiesProvider::get);
                });
    }

    @Bean
    public ServletRegistrationBean delegateServiceExporterServlet(OpenMrcClientList clients) {
        return new ServletRegistrationBean(new VishyOpenMrcServlet(clients), "/openmrc");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        int BROWSER_CACHE_CONTROL = 604800;
        registry
                .addResourceHandler("/vishy/scripts/**")
                .addResourceLocations("/vishy/scripts/")
                .setCachePeriod(BROWSER_CACHE_CONTROL);
    }
}
