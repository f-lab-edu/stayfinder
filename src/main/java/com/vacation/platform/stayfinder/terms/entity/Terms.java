package com.vacation.platform.stayfinder.terms.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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
    @Column(name = "terms_id", nullable = false)
    private int termsId;

    // 유니크 해야됨.
    @Column(name = "terms_main_title", nullable = false, length = 100)
    private String termsMainTile;

    @Column(name = "is_terms_required", nullable = false, length = 20)
    private String isTermsRequired;

    @Column(name = "is_active", nullable = false, length = 1)
    private boolean isActive;

    @OneToMany(mappedBy = "terms")
    private List<TermsSub> subList = new ArrayList<>();

}
