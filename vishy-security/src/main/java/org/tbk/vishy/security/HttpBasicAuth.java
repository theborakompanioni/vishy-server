package org.tbk.vishy.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class HttpBasicAuth {
    public static class HttpBasicAuthManagerConfigurer implements
            SecurityConfigurer<AuthenticationManager, AuthenticationManagerBuilder> {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("admin").roles("ADMIN").password("password1")
                    .and()
                    .withUser("user1").roles("BASIC").password("password2")
                    .and()
                    .withUser("guest").roles("GUEST").password("password3");

        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
        }
    }

    public static class HttpBasicAuthSecurityConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

        @Override
        public void init(HttpSecurity http) throws Exception {
            http.httpBasic();

            http.csrf().disable();
            //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
        }
    }
}