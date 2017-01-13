package org.tbk.vishy.jdbc;


import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.vishy.jdbc.OpenMrcJdbcSaveAction;
import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequest;
import org.tbk.vishy.jdbc.model.openmrc.PersistedOpenMrcRequestRepository;

import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class VishyJdbcSaveAction implements OpenMrcJdbcSaveAction {

    private final PersistedOpenMrcRequestRepository requestRepository;
    private final LinkedBlockingQueue<PersistedOpenMrcRequest> queue = new LinkedBlockingQueue<>();
    private volatile boolean enabled = true;

    public VishyJdbcSaveAction(PersistedOpenMrcRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public void apply(JdbcTemplate jdbcTemplate, OpenMrc.Request request) {
        if (!enabled) {
            return;
        }

        final PersistedOpenMrcRequest dbEntity = PersistedOpenMrcRequest
                .create(request)
                .build();

        queue.offer(dbEntity);
    }

    @Transactional
    @Scheduled(fixedDelay = 1000, initialDelay = 3000)
    void persist() {
        Collection<PersistedOpenMrcRequest> persistableRequests = Lists.newLinkedList();
        queue.drainTo(persistableRequests);

        if (!persistableRequests.isEmpty()) {
            requestRepository.save(persistableRequests);
        }
    }

    @PreDestroy
    void shutdown() {
        enabled = false;
        persist();
    }
}
