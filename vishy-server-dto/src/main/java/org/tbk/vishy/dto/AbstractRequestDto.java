package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.tbk.openmrc.core.dto.OpenMrcRequestDto;
import org.tbk.openmrc.core.dto.ViewportDto;

import java.util.Map;
import java.util.Optional;

/**
 * Created by void on 05.05.15.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = InitialEventRequestDtoImpl.class, name = "initial"),
    @JsonSubTypes.Type(value = StatusEventRequestDtoImpl.class, name = "status"),
    @JsonSubTypes.Type(value = SummaryEventRequestDtoImpl.class, name = "summary")
})
public abstract class AbstractRequestDto implements OpenMrcRequestDto {
    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("monitorId")
    public String monitorId;

    @JsonProperty("viewport")
    public Optional<ViewportDto> viewport = Optional.empty();

    @JsonProperty("vishy")
    public Optional<Map<String, Object>> custom = Optional.empty();

    public String sessionId() {
        return sessionId;
    }

    public String monitorId() {
        return monitorId;
    }

    public Optional<ViewportDto> viewport() {
        return viewport;
    }

    public Optional<Map<String, Object>> custom() {
        return custom;
    }

}
