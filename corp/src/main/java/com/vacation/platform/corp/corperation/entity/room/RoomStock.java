package com.vacation.platform.corp.corperation.entity.room;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.corp.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "room_stock", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"room_unit_id", "stock_date"})
})
@Getter
@Setter
public class RoomStock extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_stock_id")
	private Long roomStockId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_unit_id", nullable = false)
	private RoomUnit roomUnit;

	@Column(name = "stock_date")
	private LocalDate stockDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id")
	private Reservation reservation; // 예약 연결 (nullable)
}
