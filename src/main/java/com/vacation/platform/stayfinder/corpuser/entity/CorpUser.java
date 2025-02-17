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
    @Column()
    private Long corpUserId;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String businessLicense;

    @Column(nullable = false)
    private String businessAddress;

    @Column(nullable = false)
    private String rprsName;

}
