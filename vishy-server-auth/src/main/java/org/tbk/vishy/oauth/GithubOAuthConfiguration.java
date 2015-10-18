package org.tbk.vishy.oauth;

import io.buji.pac4j.filter.ClientRolesAuthorizationFilter;
import io.buji.pac4j.filter.ClientUserFilter;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.oauth.client.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Created by void on 14.10.15.
 */
@Configuration
@EnableConfigurationProperties(GithubOAuthProperties.class)
@ConditionalOnProperty("vishy.elasticsearch.enabled")
public class GithubOAuthConfiguration {

    @Autowired
    GithubOAuthProperties properties;

    @Bean
    public GitHubClient gitHubClient() {
        GitHubClient gitHubClient = new GitHubClient(properties.getKey(), properties.getSecret());
        gitHubClient.setName("githubClient");
        gitHubClient.setReadTimeout(5_000);
        gitHubClient.setConnectTimeout(5_000);
        gitHubClient.setEnableContextualRedirects(true);
        return gitHubClient;
    }

    @Bean(name = "githubRolesFilter")
    public ClientRolesAuthorizationFilter clientRolesAuthorizationFilter() {
        final ClientRolesAuthorizationFilter filter = new ClientRolesAuthorizationFilter();
        final BaseClient<Credentials, CommonProfile> gitHubClient =
                (BaseClient<Credentials, CommonProfile>) (BaseClient) gitHubClient();
        filter.setClient(gitHubClient);
        return filter;
    }

    @Bean(name = "githubUserFilter")
    public ClientUserFilter clientUserFilter() {
        final ClientUserFilter clientUserFilter = new ClientUserFilter();
        final BaseClient<Credentials, CommonProfile> gitHubClient =
                (BaseClient<Credentials, CommonProfile>) (BaseClient) gitHubClient();
        clientUserFilter.setClient(gitHubClient);
        return clientUserFilter;
    }
}
