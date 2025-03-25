package com.vacation.platform.corp.reservation.entity;

import com.vacation.platform.corp.corperation.entity.Corporation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Table(name = "ROOM_PRODUCTS")
@Entity
public class RoomProducts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(name = "available_date", nullable = false)
	private LocalDate availableDate;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false, columnDefinition = "varchar(20) default 'available'")
	private String status = "available";

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "corp_user_id")
	private Corporation corporation;
}
