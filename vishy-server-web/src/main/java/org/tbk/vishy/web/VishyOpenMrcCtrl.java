package org.tbk.vishy.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tbk.openmrc.core.OpenMrcRequestContext;
import org.tbk.openmrc.core.OpenMrcService;
import org.tbk.openmrc.core.dto.OpenMrcRequestDto;
import org.tbk.vishy.dto.AbstractRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/openmrc")
public class VishyOpenMrcCtrl {

    private final OpenMrcService openMrcService;

    private final ObjectMapper mapper;

    private final Function<Object, Map<String, Object>> asMap;

    @Autowired
    public VishyOpenMrcCtrl(OpenMrcService openMrcService, ObjectMapper mapper) {
        this.openMrcService = openMrcService;
        this.mapper = mapper;

        this.asMap = obj -> mapper.convertValue(obj, Map.class);
    }

    @RequestMapping(value = "/{clientName}/track/{userId}/{event}", method = RequestMethod.POST)
    public ResponseEntity<Void> postTrack(
        HttpServletRequest request,
        @PathVariable String clientName,
        @PathVariable String userId,
        @PathVariable String event) {

        try {
            track(request, clientName, userId, event);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.accepted().build();
    }

    private void track(HttpServletRequest request, String clientName, String userId, String event)
        throws IOException {

        OpenMrcRequestDto requestData = readAndConvertJsonRequestBody(request, AbstractRequestDto.class);
        Map<String, Object> properties = Optional.ofNullable(requestData).map(asMap).get();

        openMrcService.track(OpenMrcRequestContext.builder()
            .httpRequest(request)
            .clientName(clientName)
            .userId(userId)
            .event(event)
            .data(requestData)
            .properties(properties)
            .build());
    }

    private <T> T readAndConvertJsonRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        JsonNode jsonNode = mapper.readTree(request.getReader());
        return mapper.convertValue(jsonNode, clazz);
    }
}
