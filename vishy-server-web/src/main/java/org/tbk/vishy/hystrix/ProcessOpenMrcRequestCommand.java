package org.tbk.vishy.hystrix;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by void on 07.08.15.
 */
public class ProcessOpenMrcRequestCommand extends HystrixCommand<OpenMrc.Request> {
    private final static HystrixCommandGroupKey DEFAULT_KEY = HystrixCommandGroupKey.Factory.asKey("ProcessOpenMrcRequest");

    private final OpenMrcRequestConsumer consumer;
    private final OpenMrc.Request request;

    public ProcessOpenMrcRequestCommand(OpenMrcRequestConsumer consumer, OpenMrc.Request request) {
        super(DEFAULT_KEY);
        this.consumer = consumer;
        this.request = request;
    }

    @Override
    protected OpenMrc.Request run() {
        consumer.accept(request);
        return request;
    }
}
