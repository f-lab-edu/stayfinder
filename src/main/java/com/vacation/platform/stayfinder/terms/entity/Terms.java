package com.vacation.platform.stayfinder.terms.entity;

import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity(name = "Terms")
@NoArgsConstructor
@Data
@Table(name = "TERMS", uniqueConstraints = {@UniqueConstraint(
    name = "termsId_mainTitle",
        columnNames = {"terms_id", "terms_main_title"}
)})
@EqualsAndHashCode(callSuper = true)
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer termsId;

    // 유니크 해야됨.
    @Column(nullable = false, length = 100)
    private String termsMainTile;

    @Column(nullable = false, length = 1)
    private Boolean isTermsRequired;

    @OneToMany(mappedBy = "termsId")
    private List<TermsSub> subList = new ArrayList<>();

    @OneToMany(mappedBy = "termsId")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();
}
