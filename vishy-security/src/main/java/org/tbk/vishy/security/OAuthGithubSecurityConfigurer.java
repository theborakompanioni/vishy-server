package org.tbk.vishy.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

public class OAuthGithubSecurityConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    private final Filter ssoFilter;

    public OAuthGithubSecurityConfigurer(Filter ssoFilter) {
        this.ssoFilter = ssoFilter;
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);

        http.antMatcher("/**")
                .logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    }
}