package org.tbk.vishy.jdbc.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vishy_openmrc_request")
public class PersistedOpenMrcRequest {
    
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "type", updatable = false, nullable = false)
    private String type;

    @Column(name = "json", updatable = false, nullable = false)
    private String json;
}
