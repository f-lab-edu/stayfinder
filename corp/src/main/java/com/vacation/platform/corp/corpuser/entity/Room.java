package com.vacation.platform.corp.corpuser.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@RequiredArgsConstructor
public class Room extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, updatable = false)
	private Long roomId;

	@Column(nullable = false)
	private String roomName;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RoomCategory category;

	@Column(nullable = false)
	private int capacity;  // 수용 인원

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "corp_user_id", nullable = false)
	private CorpUser corpUser;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false) // `CorporateUser`와 연결
	private CorporateUser corporateUser; // 객실을 소유한 기업회원
}
