package org.tbk.vishy.jdbc.listener;

import org.tbk.vishy.jdbc.model.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AbstractEntityListener {

    @PreUpdate
    public void preUpdate(AbstractEntity abstractEntity) {
        abstractEntity.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist
    public void prePersist(AbstractEntity abstractEntity) {
    }

}
