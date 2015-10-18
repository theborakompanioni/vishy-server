package org.tbk.vishy.oauth;

import io.buji.pac4j.ClientFilter;
import io.buji.pac4j.ClientRealm;
import io.buji.pac4j.ClientSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by void on 14.10.15.
 */
@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
@ConditionalOnProperty("vishy.oauth.enabled")
public class Pac4jConfiguration {

    @Autowired
    private OAuthProperties properties;

    @Autowired
    private List<Client> clientList;

    @Bean
    public Clients clients() {
        Clients clients = new Clients();
        clients.setCallbackUrl(properties.getCallbackUrl());
        clients.setClientsList(clientList);
        return clients;
    }

    @Bean
    public ClientFilter clientFilter() {
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.setClients(clients());
        clientFilter.setFailureUrl("/static/client_filter_failure.html");
        clientFilter.setSuccessUrl("/static/client_filter_success.html");
        clientFilter.setRedirectAfterSuccessfulAuthentication(true);
        return clientFilter;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public ClientRealm clientRealm() {
        ClientRealm clientRealm = new ClientRealm();
        clientRealm.setDefaultRoles("USER_ROLE");
        clientRealm.setClients(clients());
        return clientRealm;
    }

    @Bean
    public ClientSubjectFactory clientSubjectFactory() {
        return new ClientSubjectFactory();
    }
}
