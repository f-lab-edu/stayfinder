package com.vacation.platform.stayfinder.certify.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "certify_req")
@EqualsAndHashCode(callSuper = true)
public class CertifyReq extends BaseEntity {

    // 인증 테이블을 공통으로 사용할수 있게 처리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "req_certify_number", nullable = false, length = 6)
    private String reqCertifyNumber;

    @Column(name = "try_number", nullable = false)
    private int tryNumber;

    // 인증 대상 enum으로 처리
    @Column(name = "certify_number", nullable = false, length = 20)
    private CertifyType certifyNumber; // 인증 대상

}
