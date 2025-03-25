package com.vacation.platform.corp.corperation.entity;

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
	private String name;

	@Column(name = "room_type", nullable = false)
	private String roomType;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer capacity;

	@Column(nullable = false)
	private String roomNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "corp_user_id")
	private Corporation corporation;

//	@OneToMany(mappedBy = "room")
//	private List<RoomProductStock> productStocks;
}