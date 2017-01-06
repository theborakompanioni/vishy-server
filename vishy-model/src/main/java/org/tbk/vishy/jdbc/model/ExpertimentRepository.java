package org.tbk.vishy.jdbc.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "experiment", path = "experiment")
public interface ExpertimentRepository extends PagingAndSortingRepository<Experiment, Long> {

}
