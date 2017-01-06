package org.tbk.vishy.hystrix;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.requireNonNull;

@Slf4j
public class HystrixOpenMrcRequestConsumer implements OpenMrcRequestConsumer {

    private final HystrixCommand.Setter setter;
    private final OpenMrcRequestConsumer consumer;

    public HystrixOpenMrcRequestConsumer(HystrixCommand.Setter setter, OpenMrcRequestConsumer consumer) {
        this.setter = requireNonNull(setter);
        this.consumer = requireNonNull(consumer);
    }

    @Override
    public void accept(OpenMrc.Request request) {
        new ProcessOpenMrcRequestConsumeCommand(setter, consumer, request)
                .toObservable()
                .subscribe();
    }
}
