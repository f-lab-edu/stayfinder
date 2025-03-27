package com.vacation.platform.corp.corperation.entity;


import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.corp.corperation.entity.room.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "corporation")
@Data
@RequiredArgsConstructor
public class Corporation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long corpUserId;

    @Column(nullable = false, columnDefinition = "사업자 명")
    private String businessName;

    @Column(nullable = false, unique = true, columnDefinition = "사업자 번호")
    private String businessLicense;

    @Column(nullable = false, columnDefinition = "사업자 주소")
    private String businessAddress;

    @Column(nullable = false, columnDefinition = "숙박업소 상호명")
    private String businessTitle;

    @Column(nullable = false, columnDefinition = "숙박업 카테고리")
    @Enumerated(EnumType.STRING)
    private BusinessCategory businessCategory;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private CorpStatus corpStatus;

    @OneToOne
    @JoinColumn(name = "corp_user_id")
    private CorporateUser corporateUser;

    @OneToMany(mappedBy = "corporation", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
}