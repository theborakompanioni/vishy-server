package org.tbk.vishy.jdbc.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project")
public class Project extends AbstractEntity {
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;
}
