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
@Table(name = "terms_sub")
public class TermsSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_sub_id")
    private int termsSubId;

    @Column(name = "terms_main_id")
    private int termsMainId;

    @Column(name = "version")
    private int version;

    @Column(name = "terms_details_title")
    private String termsDetailsTitle;

    @Column(name = "terms_details_content")
    private String termsDetailsContent;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "modify_at")
    private Timestamp modifyAt;

}
