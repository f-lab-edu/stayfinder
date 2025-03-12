package com.vacation.platform.api.terms.entity;


import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Column(name = "terms_id")
    private Long termsId;

    @ManyToOne
    @JoinColumn(name = "terms_id", insertable = false, updatable = false)
    private Terms terms;

    @Id
    @Column(name = "version", nullable = false, unique = true, columnDefinition = "약관의 버전")
    private Long version;

    @Column(name = "terms_details_title", nullable = false, length = 100, columnDefinition = "상세 약관의 타이틀")
    private String termsDetailsTitle;

    @Column(name = "terms_details_content", nullable = false, length = 10000, columnDefinition = "상세 약관의 내용")
    private String termsDetailsContent;

    @Column(name = "is_active", nullable = false, length = 1, columnDefinition = "약관의 상태")
    private Boolean isActive;
}
