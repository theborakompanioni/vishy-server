package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.InitialEventRequestDto;
import org.tbk.openmrc.core.dto.VisibilityStateDto;

public class InitialEventRequestDtoImpl extends AbstractRequestDto implements InitialEventRequestDto {
    @JsonProperty("initial")
    public boolean initial;

    @JsonProperty("state")
    public VisibilityStateDtoImpl state;

    @Override
      public VisibilityStateDto state() {
        return state;
    }

    public boolean initial() {
        return true;
    }
}
