package org.tbk.vishy.jdbc.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.stream.Stream;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    /**
     * Sample method to demonstrate support for {@link Stream} as a return type with a custom query. The query is executed
     * in a streaming fashion which means that the method returns as soon as the first results are ready.
     *
     * @return
     */
    @Query("select c from Customer c")
    Stream<Customer> streamAllCustomers();

    /**
     * Sample method to demonstrate support for {@link Stream} as a return type with a derived query. The query is
     * executed in a streaming fashion which means that the method returns as soon as the first results are ready.
     *
     * @return
     */
    Stream<Customer> findAllByNameIsNotNull();
}
