package com.vacation.platform.stayfinder.certify.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "CERTIFY_REQ", uniqueConstraints={@UniqueConstraint(
    name = "id_userId_reqCertifyNumber",
        columnNames = {"id", "user_id", "req_certify_number"}
)})
@EqualsAndHashCode(callSuper = true)
public class CertifyReq extends BaseEntity {

    // 인증 테이블을 공통으로 사용할수 있게 처리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 6)
    private String reqCertifyNumber;

    @Column(nullable = false)
    private Integer tryNumber;

    // 인증 대상 enum으로 처리
    @Column(name = "certify_number", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CertifyType certifyNumber; // 인증 대상

}
