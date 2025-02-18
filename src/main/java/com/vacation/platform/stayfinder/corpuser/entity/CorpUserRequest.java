package com.vacation.platform.stayfinder.corpuser.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "corp_user_request")
@RequiredArgsConstructor
public class CorpUserRequest{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false, columnDefinition = "사업자명")
    private String businessName;

    @Column(nullable = false, unique = true, columnDefinition = "사업자 번호")
    private String businessLicense;

    @Column(nullable = false, columnDefinition = "사업자 주소")
    private String businessAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(nullable = false, columnDefinition = "숙박업소 상호명")
    private String businessTitle;

    @Column(nullable = false, columnDefinition = "숙박업 카테고리")
    @Enumerated(EnumType.STRING)
    private BusinessCategory businessCategory;

    @CreatedDate
    @Column(columnDefinition = "생성일자")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "승인일자")
    private  LocalDateTime approvedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "corpUserRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessLicenseFile> businessLicenseFiles = new ArrayList<>();
}
