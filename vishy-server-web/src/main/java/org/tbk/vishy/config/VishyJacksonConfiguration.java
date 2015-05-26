package org.tbk.vishy.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.openmrc.core.dto.*;
import org.tbk.vishy.dto.*;

@Configuration
public class VishyJacksonConfiguration {

    @Bean
    public SimpleModule jacksonVishyModule() {
        Version version = new Version(1, 0, 0, "SNAPSHOT", "org.tbk.vishy", "openmrc");
        SimpleModule module = new SimpleModule("VishyModule", version);

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();

        resolver.addMapping(InitialEventRequestDto.class, InitialEventRequestDtoImpl.class);
        resolver.addMapping(SummaryEventRequestDto.class, SummaryEventRequestDtoImpl.class);
        resolver.addMapping(StatusEventRequestDto.class, StatusEventRequestDtoImpl.class);

        resolver.addMapping(ViewportDto.class, ViewportDtoImpl.class);
        resolver.addMapping(VisibilityStateDto.class, VisibilityStateDtoImpl.class);
        resolver.addMapping(VisibilityTimeTestConfigDto.class, VisibilityTimeTestConfigDtoImpl.class);
        resolver.addMapping(VisibilityTimeReportDto.class, VisibilityTimeReportDtoImpl.class);
        resolver.addMapping(PercentageTimeTestDto.class, PercentageTimeTestDtoImpl.class);

        module.setAbstractTypes(resolver);

        return module;
    }

    @Bean
    public Jdk8Module jacksonJdk8Module() {
        return new Jdk8Module();
    }


}
