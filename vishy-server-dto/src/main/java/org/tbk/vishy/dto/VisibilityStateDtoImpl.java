package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.VisibilityStateDto;

public class VisibilityStateDtoImpl implements VisibilityStateDto {
    @JsonProperty("code")
    public int code;

    @JsonProperty("state")
    public String state;

    @JsonProperty("percentage")
    public float percentage;

    @JsonProperty("fullyvisible")
    public boolean fullyvisible;

    @JsonProperty("visible")
    public boolean visible;

    @JsonProperty("hidden")
    public boolean hidden;

    @Override
    public int code() {
        return code;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public float percentage() {
        return percentage;
    }

    @Override
    public boolean fullyvisible() {
        return fullyvisible;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public boolean hidden() {
        return hidden;
    }
}
