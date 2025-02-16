package com.vacation.platform.stayfinder.corpuser.entity;


import com.vacation.platform.stayfinder.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "corp_user")
@Data
@RequiredArgsConstructor
public class CorpUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corp_user_id")
    private Long corpUserId;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_license", nullable = false, unique = true)
    private String businessLicense;

    @Column(name = "business_address", nullable = false)
    private String businessAddress;
}
