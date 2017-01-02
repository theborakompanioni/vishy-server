package org.tbk.vishy.hystrix;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import rx.Observer;

import static java.util.Objects.requireNonNull;

@Slf4j
public class HystrixOpenMrcRequestInterceptor implements OpenMrcRequestConsumer {

    private final HystrixCommand.Setter setter;
    private final OpenMrcRequestConsumer consumer;

    public HystrixOpenMrcRequestInterceptor(HystrixCommand.Setter setter, OpenMrcRequestConsumer consumer) {
        this.setter = requireNonNull(setter);
        this.consumer = requireNonNull(consumer);
    }

    @Override
    public void accept(OpenMrc.Request request) {
        new ProcessOpenMrcRequestCommand(setter, consumer, request).toObservable()
                .subscribe(new Observer<OpenMrc.Request>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        log.warn("", e);
                    }

                    @Override
                    public void onNext(OpenMrc.Request request) {
                        log.debug("finished processing request interceptor '{}' with hystrix", consumer.getClass().getSimpleName());
                    }
                });
    }
}
