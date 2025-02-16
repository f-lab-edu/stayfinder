package com.vacation.platform.stayfinder.corpuser.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "corp_user_request")
@RequiredArgsConstructor
public class CorpUserRequest  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "사업자명")
    private String businessName;

    @Column(nullable = false, unique = true, columnDefinition = "사업자 번호")
    private String businessLicense;

    @Column(nullable = false, columnDefinition = "사업자 주소")
    private String businessAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;
}
