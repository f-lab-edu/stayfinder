package com.vacation.platform.stayfinder.user.entity;

import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Data
@NoArgsConstructor
@Table
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;
    
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "userId")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickName, String phoneNumber, String birthday, Gender gender) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
    }
}
