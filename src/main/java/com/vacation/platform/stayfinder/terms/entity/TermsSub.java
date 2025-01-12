package com.vacation.platform.stayfinder.terms.entity;


import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "TERMS_SUB", uniqueConstraints = {@UniqueConstraint(
        name = "termsMainId_version",
        columnNames = {"terms_id", "version"}
)})
@EqualsAndHashCode(callSuper = true)
@IdClass(TermsSubId.class)
public class TermsSub extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "terms_id")
    private Terms termsId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, length = 100)
    private String termsDetailsTitle;

    @Column(nullable = false, length = 10000)
    private String termsDetailsContent;

    @Column(nullable = false, length = 1)
    private Boolean isActive;

    @OneToMany(mappedBy = "version")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();
}
