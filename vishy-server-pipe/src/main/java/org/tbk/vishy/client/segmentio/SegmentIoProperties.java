package org.tbk.vishy.client.segmentio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("vishy.segmentio")
public class SegmentIoProperties {

    private boolean enabled;
    private String writeKey;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getWriteKey() {
        return writeKey;
    }

    public void setWriteKey(String writeKey) {
        this.writeKey = writeKey;
    }
}