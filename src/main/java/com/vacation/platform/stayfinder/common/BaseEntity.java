package com.vacation.platform.stayfinder.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreatedDate
    private Long createAt;

    @LastModifiedDate
    private Long modifyAt;

    @PrePersist
    public void prePersist() {
        long currentTimeMillis = System.currentTimeMillis();
        this.createAt = currentTimeMillis;
        this.modifyAt = currentTimeMillis;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyAt = System.currentTimeMillis();
    }

}
