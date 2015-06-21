package org.tbk.vishy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tbk.openmrc.OpenMrc;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by void on 21.06.15.
 */
public class RequestToMapFunction implements Function<OpenMrc.Request, Map<String, Object>> {

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    public static RequestToMapFunction create() {
        return new RequestToMapFunction(DEFAULT_MAPPER);
    }

    public static RequestToMapFunction create(ObjectMapper mapper) {
        return new RequestToMapFunction(mapper);
    }

    private final ObjectMapper mapper;

    private RequestToMapFunction(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> apply(OpenMrc.Request request) {
        return mapper.valueToTree(request);
    }
}
