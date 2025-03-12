package com.vacation.platform.api.terms.entity;

import com.vacation.platform.api.certify.entity.TermsUserAgreement;
import com.vacation.platform.api.common.BaseEntity;
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
    @Column(name = "terms_id",nullable = false, columnDefinition = "테이블 id")
    private Long termsId;

    @Column(name = "terms_main_title", nullable = false, length = 100, unique = true, columnDefinition = "메인 약관 제목")
    private String termsMainTitle;

    @Column(name = "is_terms_required", nullable = false, length = 1, columnDefinition = "메인 약관 필수 여부")
    private Boolean isTermsRequired;

    @Column(name = "sort_seq", nullable = false, length = 1, columnDefinition = "약관 보이는 순서")
    private Integer sortSeq;

    @OneToMany(mappedBy = "terms")
    private List<TermsSub> subList = new ArrayList<>();

    @OneToMany(mappedBy = "termsId")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();
}
