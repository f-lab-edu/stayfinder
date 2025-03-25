package com.vacation.platform.corp.corperation.entity;

import com.vacation.platform.corp.reservation.entity.RoomProducts;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
		name = "room_product_stock",
		uniqueConstraints = @UniqueConstraint(name = "uq_corp_date_type", columnNames = {"corp_user_id", "available_date", "room_type"})
)
@Data
@RequiredArgsConstructor
public class RoomProductStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "available_date", nullable = false)
	private LocalDate availableDate;

	@Column(name = "room_type", nullable = false)
	private String roomType;

	@Column(name = "room_id")
	private Long roomId; // 예약 확정 시 연결 (nullable)

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private RoomStockStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private RoomProducts product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "corp_user_id", nullable = false)
	private Corporation corporation;
}
