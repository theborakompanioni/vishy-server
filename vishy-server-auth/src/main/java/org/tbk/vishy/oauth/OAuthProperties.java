package org.tbk.vishy.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by void on 15.10.15.
 */

@ConfigurationProperties("vishy.oauth")
public class OAuthProperties {
    private boolean enabled;
    private String callbackUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
