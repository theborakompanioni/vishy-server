package org.tbk.vishy.oauth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by void on 15.10.15.
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public InitializingBean initSecurityManager(SecurityManager securityManager) {
        return () -> SecurityUtils.setSecurityManager(securityManager);
    }

    @Bean
    public FilterRegistrationBean contextFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
                Subject subject = ThreadContext.getSubject();
                if (subject == null) {
                    subject = (new WebSubject.Builder(request, response)).buildSubject();
                    ThreadContext.bind(subject);
                }
                chain.doFilter(request, response);
            }
        });
        return registrationBean;
    }

    @Bean
    /*@DependsOn({"clientFilter", "githubUserFilter", "githubRolesFilter"})*/
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/static/unauthorized.html");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap());

        return factoryBean;
    }

    /*@Bean
    public FilterRegistrationBean shiroFilterRegistration(AbstractShiroFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        return registrationBean;
    }*/


    private Map<String, String> filterChainDefinitionMap() {
        Map<String, String> map = new HashMap<>();

        map.put("/static/admin.html", "githubRolesFilter[USER_ROLE]");

        map.put("/static/user.html", "githubUserFilter");

        map.put("/logout", "logout");

        map.put("/oauth_callback", "clientFilter");

        //map.put("/**", "githubRolesFilter[USER_ROLE]");
        map.put("/**", "anon");

        map.put("/swagger-ui.html", "anon");

        return map;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(Realm realm, SubjectFactory subjectFactory) {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    @Bean
    public MemoryConstrainedCacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        return new CookieRememberMeManager();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao());
        sessionManager.setGlobalSessionTimeout(43200000); // 12 hours
        sessionManager.setCacheManager(cacheManager());
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDao() {
        return new MemorySessionDAO();
    }

    /*@Bean(name = "passwordService")
    public DefaultPasswordService passwordService() {
        return new DefaultPasswordService();
    }*/

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
