package com.vacation.platform.stayfinder.terms.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table
@EqualsAndHashCode(callSuper = true)
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
//    @OneToMany
    private int termsId;

    // 유니크 해야됨.
    @Column(name = "terms_main_title")
    private String termsMainTile;

    @Column(name = "is_terms_required")
    private String isTermsRequired;

    @Column(name = "is_active")
    private boolean isActive;

    //부모로 뺴줘야한다.
    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "modify_at")
    private Timestamp modifyAt;

}
