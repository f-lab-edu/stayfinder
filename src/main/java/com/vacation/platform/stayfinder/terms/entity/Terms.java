package com.vacation.platform.stayfinder.terms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "terms")
public class Terms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private int termsId;

    @Column(name = "terms_main_title")
    private String termsMainTile;

    @Column(name = "is_terms_required")
    private String isTermsRequired;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "modify_at")
    private Timestamp modifyAt;

}
