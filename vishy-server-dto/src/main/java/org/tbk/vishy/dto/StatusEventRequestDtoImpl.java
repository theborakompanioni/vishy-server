package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.PercentageTimeTestDto;
import org.tbk.openmrc.core.dto.StatusEventRequestDto;

public class StatusEventRequestDtoImpl extends AbstractRequestDto implements StatusEventRequestDto {
    @JsonProperty("test")
    public PercentageTimeTestDtoImpl test;

    @Override
    public PercentageTimeTestDto test() {
        return test;
    }
}
