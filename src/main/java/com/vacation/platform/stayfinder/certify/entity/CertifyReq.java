package com.vacation.platform.stayfinder.certify.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Entity
@NoArgsConstructor
@Data
@Table(name = "certify_req")
@EqualsAndHashCode(callSuper = true)
public class CertifyReq extends BaseEntity {

    // 인증 테이블을 공통으로 사용할수 있게 처리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;

    // create_at 을 사용하는게 좋음
    private Timestamp reqTime;

    private Timestamp resTime;

    private String reqCertifyNumber;

    private int tryNumber;

    // 삭제 대상
    // 인증 대상 enum으로 처리
    private String certifyPhoneNumber;

}
