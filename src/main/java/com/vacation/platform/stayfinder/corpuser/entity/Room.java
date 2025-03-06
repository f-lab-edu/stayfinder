package com.vacation.platform.stayfinder.corpuser.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vacation.platform.stayfinder.common.BaseEntity;
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

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "corp_user_id", nullable = false)
	private CorporateUser corporateUser;
}
