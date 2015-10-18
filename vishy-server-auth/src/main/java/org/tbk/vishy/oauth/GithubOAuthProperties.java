package org.tbk.vishy.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by void on 15.10.15.
 */

@ConfigurationProperties("vishy.oauth.github")
public class GithubOAuthProperties {
    private boolean enabled;
    private String key;
    private String secret;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
