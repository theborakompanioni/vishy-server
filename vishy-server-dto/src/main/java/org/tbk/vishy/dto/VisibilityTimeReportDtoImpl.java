package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.VisibilityTimeReportDto;

public class VisibilityTimeReportDtoImpl implements VisibilityTimeReportDto  {

    @JsonProperty("duration")
    public long duration;

    @JsonProperty("timeHidden")
    public long timeHidden;

    @JsonProperty("timeVisible")
    public long timeVisible;

    @JsonProperty("timeFullyVisible")
    public long timeFullyVisible;

    @JsonProperty("timeRelativeVisible")
    public long timeRelativeVisible;

    @JsonProperty("percentage")
    public PercentageDtoImpl percentage;

    @Override
    public long duration() {
        return duration;
    }

    @Override
    public long timeHidden() {
        return timeHidden;
    }

    @Override
    public long timeVisible() {
        return timeVisible;
    }

    @Override
    public long timeFullyVisible() {
        return timeFullyVisible;
    }

    @Override
    public long timeRelativeVisible() {
        return timeRelativeVisible;
    }

    @Override
    public PercentageDto percentage() {
        return percentage;
    }

    public static class PercentageDtoImpl implements PercentageDto {
        @JsonProperty("current")
        public float current;

        @JsonProperty("max")
        public float max;

        @JsonProperty("min")
        public float min;

        @Override
        public float current() {
            return current;
        }

        @Override
        public float max() {
            return max;
        }

        @Override
        public float min() {
            return min;
        }
    }
}
