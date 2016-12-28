package org.tbk.vishy.client.analytics;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("vishy.js.analytics")
public class VishyAnalyticsScriptProperties {

    private boolean enabled;
    private String host;
    private String port;
    private String protocol;
    private String scriptSrc;

    public VishyAnalyticsScriptProperties() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return this.port;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getScriptSrc() {
        return scriptSrc;
    }

    public void setScriptSrc(String scriptSrc) {
        this.scriptSrc = scriptSrc;
    }
}
