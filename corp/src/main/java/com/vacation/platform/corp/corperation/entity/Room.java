package com.vacation.platform.corp.corperation.entity;

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
	private String name;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private int capacity;

	@Column(nullable = false)
	private String room_number;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "corp_user_id", nullable = false)
	private Corporation corporation;

	@ManyToOne
	@JoinColumn(name = "room_type_id", nullable = false)
	private RoomType roomType;
}
