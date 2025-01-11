package com.vacation.platform.stayfinder.user.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Data
@NoArgsConstructor
@Table
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    private int userId;

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
    private boolean isActive;

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
