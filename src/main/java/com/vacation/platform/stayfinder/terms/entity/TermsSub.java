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
    @Column(nullable = false, unique = true, columnDefinition = "약관의 버전")
    private Integer version;

    @Column(nullable = false, length = 100, columnDefinition = "상세 약관의 타이틀")
    private String termsDetailsTitle;

    @Column(nullable = false, length = 10000, columnDefinition = "상세 약관의 내용")
    private String termsDetailsContent;

    @Column(nullable = false, length = 1, columnDefinition = "약관의 상태")
    private Boolean isActive;

    @OneToMany(mappedBy = "version")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();
}
