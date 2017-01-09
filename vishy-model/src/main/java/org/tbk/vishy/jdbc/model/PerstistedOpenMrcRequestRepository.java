package org.tbk.vishy.jdbc.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "request", path = "request")
public interface PerstistedOpenMrcRequestRepository extends PagingAndSortingRepository<PersistedOpenMrcRequest, Long> {

}
