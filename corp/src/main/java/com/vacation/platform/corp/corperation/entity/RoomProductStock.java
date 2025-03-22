package com.vacation.platform.corp.corperation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "room_product_stock")
@Data
@RequiredArgsConstructor
public class RoomProductStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private RoomStockStatus status;
}
