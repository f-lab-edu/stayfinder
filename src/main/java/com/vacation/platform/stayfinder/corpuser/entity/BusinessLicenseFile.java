package com.vacation.platform.stayfinder.corpuser.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "business_license_file")
@Data
@RequiredArgsConstructor
public class BusinessLicenseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "corp_user_request_id")
    private Long corpUserRequestId;

    @Column
    private String fileUrl;

    @Column(nullable = false)
    private String fileName;

    @Column
    private String fileType;

    @Column
    private Long fileSize;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "corp_user_request_id", insertable = false, updatable = false)
    private CorpUserRequest corpUserRequest;

}
