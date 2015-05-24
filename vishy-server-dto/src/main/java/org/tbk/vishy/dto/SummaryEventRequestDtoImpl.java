package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.SummaryEventRequestDto;
import org.tbk.openmrc.core.dto.VisibilityTimeReportDto;

public class SummaryEventRequestDtoImpl extends AbstractRequestDto implements SummaryEventRequestDto {
    @JsonProperty("report")
    public VisibilityTimeReportDtoImpl report;

    @Override
    public VisibilityTimeReportDto report() {
        return report;
    }
}
