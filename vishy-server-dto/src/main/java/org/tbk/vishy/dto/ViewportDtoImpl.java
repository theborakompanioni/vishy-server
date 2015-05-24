package org.tbk.vishy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tbk.openmrc.core.dto.ViewportDto;

public class ViewportDtoImpl implements ViewportDto {
    @JsonProperty("width")
    public int width;

    @JsonProperty("height")
    public int height;


    public int  width() {
        return width;
    }

    public int  height() {
        return height;
    }
}
