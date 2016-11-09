package org.tbk.vishy.client;

import com.github.theborakompanioni.openmrc.OpenMrc;

import java.util.Map;
import java.util.function.Function;

public interface OpenMrcRequestToMapFunction extends Function<OpenMrc.Request, Map<String, Object>> {
}
