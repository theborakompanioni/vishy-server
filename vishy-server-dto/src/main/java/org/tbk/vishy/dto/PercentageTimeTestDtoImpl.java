package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.PercentageTimeTestDto;
import org.tbk.openmrc.core.dto.VisibilityStateDto;
import org.tbk.openmrc.core.dto.VisibilityTimeReportDto;
import org.tbk.openmrc.core.dto.VisibilityTimeTestConfigDto;

public class PercentageTimeTestDtoImpl implements PercentageTimeTestDto {
    @JsonProperty("monitorState")
    public VisibilityStateDtoImpl monitorState;

    @JsonProperty("testConfig")
    public VisibilityTimeTestConfigDtoImpl testConfig;

    @JsonProperty("timeReport")
    public VisibilityTimeReportDtoImpl timeReport;

    @Override
    public VisibilityStateDto monitorState() {
        return monitorState;
    }

    @Override
    public VisibilityTimeTestConfigDto testConfig() {
        return testConfig;
    }

    @Override
    public VisibilityTimeReportDto timeReport() {
        return timeReport;
    }
}
