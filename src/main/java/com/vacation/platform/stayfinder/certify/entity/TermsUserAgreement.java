package com.vacation.platform.stayfinder.certify.entity;

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
public class TermsUserAgreement extends BaseEntity {

    // 필요 없음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // 아래 3개로 복합키

    // 유저 id로 걸어야함
    @Column(name = "phone_number")
    private String phoneNumber;

    // termsSub의 mainId
    @Column(name = "terms_id")
    private int termsId;

    // version도 들어와야됨.


}
