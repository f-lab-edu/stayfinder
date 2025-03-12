package com.vacation.platform.api.user.entity;

import com.vacation.platform.api.certify.entity.CertifyReq;
import com.vacation.platform.api.certify.entity.TermsUserAgreement;
import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.login.entity.UserAuth;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "유저 id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "이메일")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "비밀번호")
    private String password;

    @Column(name = "nick_name",nullable = false, unique = true, columnDefinition = "닉네임")
    private String nickName;
    
    @Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "전화번호")
    private String phoneNumber;

    @Column(name = "birthday", columnDefinition = "생년월일")
    private String birthday;

    @Column(name = "gender", columnDefinition = "성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_status", nullable = false, columnDefinition = "회원상태")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "role", nullable = false, columnDefinition = "권한")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userId")
    private List<TermsUserAgreement> termsUserAgreementList = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<CertifyReq> certifyReqList = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<UserAuth> userAuth = new ArrayList<>();
}
