package com.vacation.platform.stayfinder.certify.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "TERMS_USER_AGREEMENT", uniqueConstraints = {@UniqueConstraint(
    name = "id_userId_termsId_version",
        columnNames = {"id", "user_id", "terms_id", "version"}
)})
@IdClass(TermsUserAgreementId.class)
public class TermsUserAgreement extends BaseEntity {

    // 필요 없음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "terms_id")
    private Terms termsId;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "version", referencedColumnName = "version"),
                 @JoinColumn(name = "terms_id", referencedColumnName = "terms_id")})
    private TermsSub version;

}
