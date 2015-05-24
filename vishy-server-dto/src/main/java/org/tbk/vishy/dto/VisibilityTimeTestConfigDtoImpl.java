package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.VisibilityTimeTestConfigDto;

public class VisibilityTimeTestConfigDtoImpl implements VisibilityTimeTestConfigDto {
    @JsonProperty("interval")
    public long interval;

    @JsonProperty("percentageLimit")
    public float percentageLimit;

    @JsonProperty("timeLimit")
    public long timeLimit;

    @Override
    public long interval() {
        return interval;
    }

    @Override
    public float percentageLimit() {
        return percentageLimit;
    }

    @Override
    public long timeLimit() {
        return timeLimit;
    }
}
