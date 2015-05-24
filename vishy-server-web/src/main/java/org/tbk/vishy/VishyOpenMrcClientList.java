package org.tbk.vishy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.tbk.openmrc.core.client.OpenMrcClient;
import org.tbk.openmrc.core.client.OpenMrcClientList;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * Created by void on 01.05.15.
 */
public class VishyOpenMrcClientList implements OpenMrcClientList {
    private final List<OpenMrcClient> clients;
    private final Map<String, OpenMrcClient> clientsMap;

    public VishyOpenMrcClientList(List<OpenMrcClient> clients) {
        this.clients = ImmutableList.copyOf(Objects.requireNonNull(clients));

        this.clientsMap = ImmutableMap.copyOf(clients.stream()
            .collect(Collectors.toMap(OpenMrcClient::name, identity())));
    }

    public Optional<OpenMrcClient> get(String name) {
        return Optional.ofNullable(clientsMap.get(name));
    }

    public List<OpenMrcClient> list() {
        return clients;
    }
}
