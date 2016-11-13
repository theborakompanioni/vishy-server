package org.tbk.vishy.hystrix;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class HystrixVishyOpenMrcHttpRequestService extends OpenMrcHttpRequestService {
    private final List<OpenMrcRequestConsumer> requestConsumer;
    private final HystrixCommand.Setter setter;

    public HystrixVishyOpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper,
                                                 HystrixCommand.Setter setter,
                                                 List<OpenMrcRequestConsumer> requestConsumer) {
        super(mapper, requestConsumer);
        this.requestConsumer = requireNonNull(requestConsumer);
        this.setter = requireNonNull(setter);
    }

    @Override
    public OpenMrc.Response.Builder processRequest(OpenMrc.Request request) {
        requestConsumer.stream()
                .map(consumer -> new ProcessOpenMrcRequestCommand(setter, consumer, request))
                .forEach(ProcessOpenMrcRequestCommand::execute);

        return OpenMrc.Response.newBuilder().setId(UUID.randomUUID().toString());
    }

}
