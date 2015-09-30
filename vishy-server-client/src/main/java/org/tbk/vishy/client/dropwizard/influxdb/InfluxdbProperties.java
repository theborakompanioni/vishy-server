package org.tbk.vishy.client.dropwizard.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("vishy.influxdb")
public class InfluxdbProperties {

    private boolean enabled;
    private String hostVariable;
    private String portVariable;
    private String database;
    private String username;
    private String password;
    private boolean skipIdleMetrics;
    private long intervalInSeconds;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostVariable() {
        return hostVariable;
    }

    public void setHostVariable(String hostVariable) {
        this.hostVariable = hostVariable;
    }

    public String getPortVariable() {
        return portVariable;
    }

    public void setPortVariable(String portVariable) {
        this.portVariable = portVariable;
    }

    public boolean isSkipIdleMetrics() {
        return skipIdleMetrics;
    }

    public void setSkipIdleMetrics(boolean skipIdleMetrics) {
        this.skipIdleMetrics = skipIdleMetrics;
    }

    public long getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(long intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }
}