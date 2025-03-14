package com.vacation.platform.corp.reservation.entity;

import com.vacation.platform.corp.corpuser.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Table(name = "PRODUCTS")
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;  // 상품이 속한 객실

	@Column(name = "available_date", nullable = false)
	private LocalDate availableDate;  // 상품 예약 가능한 날짜

	@Column(nullable = false)
	private Double price;  // 상품 가격

	@Column(nullable = false, columnDefinition = "varchar(20) default 'available'")
	private String status = "available";  // 상품 상태 (기본값: available)

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;  // 상품 등록 일자

}
