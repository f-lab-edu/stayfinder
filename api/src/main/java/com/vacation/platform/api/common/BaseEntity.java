package com.vacation.platform.api.common;

import jakarta.persistence.*;
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
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "modify_at")
    private LocalDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.modifyAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyAt = LocalDateTime.now();
    }

}
