package com.vacation.platform.stayfinder.terms.entity;


import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.user.entity.TermsSubId;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "TERMS_SUB", uniqueConstraints = {@UniqueConstraint(
        name = "termsMainId_version",
        columnNames = {"terms_main_id", "version"}
)})
@EqualsAndHashCode(callSuper = true)
@IdClass(TermsSubId.class) // 복합 키 클래스 설정
public class TermsSub extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "terms_id")
    private Terms termsMainId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "terms_details_title", nullable = false, length = 100)
    private String termsDetailsTitle;

    @Column(name = "terms_details_content", nullable = false, length = 10000)
    private String termsDetailsContent;

    @Column(name = "is_active", nullable = false, length = 1)
    private boolean isActive;
}
