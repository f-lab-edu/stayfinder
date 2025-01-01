package com.vacation.platform.stayfinder.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "terms_user_agreement")
public class TermsUserAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "terms_id")
    private int termsId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modify_at")
    private Timestamp modifyAt;

}
