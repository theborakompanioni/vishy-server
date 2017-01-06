package org.tbk.vishy.jdbc.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "customer")
public class Customer extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;
}
