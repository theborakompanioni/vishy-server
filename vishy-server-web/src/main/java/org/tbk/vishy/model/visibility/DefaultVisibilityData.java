package org.tbk.vishy.model.visibility;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by void on 26.12.14.
 */
public class DefaultVisibilityData implements VisibilityData {

    @JsonProperty("duration")
    private long totalTime;

    @JsonProperty("timeHidden")
    private long hiddenTime;

    @JsonProperty("timeVisible")
    private long visibleTime;

    @JsonProperty("timeFullyVisible")
    private long fullyVisibleTime;

    @JsonProperty("timeRelativeVisible")
    private long relativeTime;

    private int maxPercentage;
    private int minPercentage;

    public DefaultVisibilityData() {
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public long getHiddenTime() {
        return this.hiddenTime;
    }

    public long getVisibleTime() {
        return this.visibleTime;
    }

    public long getFullyVisibleTime() {
        return this.fullyVisibleTime;
    }

    public long getRelativeTime() {
        return this.relativeTime;
    }

    public int getMaxPercentage() {
        return this.maxPercentage;
    }

    public int getMinPercentage() {
        return this.minPercentage;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public void setHiddenTime(long hiddenTime) {
        this.hiddenTime = hiddenTime;
    }

    public void setVisibleTime(long visibleTime) {
        this.visibleTime = visibleTime;
    }

    public void setFullyVisibleTime(long fullyVisibleTime) {
        this.fullyVisibleTime = fullyVisibleTime;
    }

    public void setRelativeTime(long relativeTime) {
        this.relativeTime = relativeTime;
    }

    public void setMaxPercentage(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public void setMinPercentage(int minPercentage) {
        this.minPercentage = minPercentage;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DefaultVisibilityData)) return false;
        final DefaultVisibilityData other = (DefaultVisibilityData) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.totalTime != other.totalTime) return false;
        if (this.hiddenTime != other.hiddenTime) return false;
        if (this.visibleTime != other.visibleTime) return false;
        if (this.fullyVisibleTime != other.fullyVisibleTime) return false;
        if (this.relativeTime != other.relativeTime) return false;
        if (this.maxPercentage != other.maxPercentage) return false;
        if (this.minPercentage != other.minPercentage) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $totalTime = this.totalTime;
        result = result * PRIME + (int) ($totalTime >>> 32 ^ $totalTime);
        final long $hiddenTime = this.hiddenTime;
        result = result * PRIME + (int) ($hiddenTime >>> 32 ^ $hiddenTime);
        final long $visibleTime = this.visibleTime;
        result = result * PRIME + (int) ($visibleTime >>> 32 ^ $visibleTime);
        final long $fullyVisibleTime = this.fullyVisibleTime;
        result = result * PRIME + (int) ($fullyVisibleTime >>> 32 ^ $fullyVisibleTime);
        final long $relativeTime = this.relativeTime;
        result = result * PRIME + (int) ($relativeTime >>> 32 ^ $relativeTime);
        result = result * PRIME + this.maxPercentage;
        result = result * PRIME + this.minPercentage;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof DefaultVisibilityData;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("totalTime", this.getTotalTime())
            .add("hiddenTime", this.getHiddenTime())
            .add("visibleTime", this.getVisibleTime())
            .add("fullyVisibleTime", this.getFullyVisibleTime())
            .add("relativeTime", this.getRelativeTime())
            .add("maxPercentage", this.getMaxPercentage())
            .add("minPercentage", this.getMinPercentage())
            .toString();
    }
}
