package org.tbk.vishy.jdbc.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.tbk.vishy.jdbc.listener.AbstractEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners({
        AbstractEntityListener.class,
        AuditingEntityListener.class
})
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime updatedAt;
}
