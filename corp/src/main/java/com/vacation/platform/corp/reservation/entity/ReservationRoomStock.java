package com.vacation.platform.corp.reservation.entity;

import com.vacation.platform.corp.corperation.entity.room.RoomStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "reservation_room_stock", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"reservation_id", "room_stock_id"})
})
@Getter
@Setter
public class ReservationRoomStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id", nullable = false)
	private Reservation reservation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_stock_id", nullable = false)
	private RoomStock roomStock;

	@Column(name = "created_at")
	private Timestamp createdAt;

}
