package com.vacation.platform.stayfinder.certify.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "req_certify_number", nullable = false, length = 6)
    private String reqCertifyNumber;

    @Column(name = "try_number", nullable = false)
    private Integer tryNumber;

    // 인증 대상 enum으로 처리
    @Column(name = "certify_number", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CertifyType certifyNumber; // 인증 대상

}
