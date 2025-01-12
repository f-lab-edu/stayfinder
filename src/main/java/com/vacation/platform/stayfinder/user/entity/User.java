package com.vacation.platform.stayfinder.user.entity;

import com.vacation.platform.stayfinder.certify.entity.TermsUserAgreement;
import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    // 유니크
    private String email;

    private String password;

    //유니크
    private String nickName;
    
    //유니크
    private String phoneNumber;

    private String birthday;
    
    private Gender gender;

    // enum으로 처리
    private Boolean isActive;

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
