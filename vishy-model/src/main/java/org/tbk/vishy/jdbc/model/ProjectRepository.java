package org.tbk.vishy.jdbc.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.stream.Stream;

@RepositoryRestResource(collectionResourceRel = "project", path = "project")
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

    @Query("select p from Project p where p.customerId = :id")
    Stream<Customer> streamAllProjectsByCustomerId(@Param("id") Long customerId);

    default Stream<Customer> streamAllProjectsByCustomer(Customer customer) {
        return streamAllProjectsByCustomerId(customer.getId());
    }
}
