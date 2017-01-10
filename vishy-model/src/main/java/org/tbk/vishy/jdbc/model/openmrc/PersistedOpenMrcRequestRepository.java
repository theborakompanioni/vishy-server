package org.tbk.vishy.jdbc.model.openmrc;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "request", path = "request")
public interface PersistedOpenMrcRequestRepository extends PagingAndSortingRepository<PersistedOpenMrcRequest, Long> {

}
