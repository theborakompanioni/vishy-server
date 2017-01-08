package org.tbk.vishy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuthGithubSecurityConfigurer githubSecurityConfigurer;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.apply(new HttpBasicAuth.HttpBasicAuthManagerConfigurer());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(githubSecurityConfigurer);
        http.apply(new HttpBasicAuth.HttpBasicAuthSecurityConfigurer());

        http.authorizeRequests()
                .antMatchers("/api/**")
                .fullyAuthenticated();
    }

}