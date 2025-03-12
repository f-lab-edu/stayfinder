package com.vacation.platform.api.certify.entity;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.user.entity.User;
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
    name = "id_userId",
        columnNames = {"id", "user_id"}
)})
@EqualsAndHashCode(callSuper = true)
@IdClass(CertifyReqId.class)
public class CertifyReq extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "req_certify_number", nullable = false, length = 6)
    private String reqCertifyNumber;

    @Column(name = "try_number", nullable = false)
    private Integer tryNumber;

    @Column(name = "certify_number", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CertifyType certifyNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

}
