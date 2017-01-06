package org.tbk.vishy.jdbc.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "experiment")
public class Experiment extends AbstractEntity {
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;
}
