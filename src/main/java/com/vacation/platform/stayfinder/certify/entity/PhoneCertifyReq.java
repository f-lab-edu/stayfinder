package com.vacation.platform.stayfinder.certify.entity;

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
@Table(name = "phone_certify_req")
public class PhoneCertifyReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "req_time")
    private Timestamp reqTime;

    @Column(name = "res_time")
    private Timestamp resTime;

    @Column(name = "req_certify_number")
    private String reqCertifyNumber;

    @Column(name = "try_number")
    private int tryNumber;

    @Column(name = "certify_phone_number")
    private String certifyPhoneNumber;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "modify_at")
    private Timestamp modifyAt;
}
