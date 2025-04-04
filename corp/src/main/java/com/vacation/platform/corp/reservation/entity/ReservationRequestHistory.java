package com.vacation.platform.corp.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reservation_request_history")
@Setter
@Getter
@RequiredArgsConstructor
public class ReservationRequestHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long reservationId;

	@Column
	private Long userId;

	@Column
	private Long roomId;

	@Column
	private Long roomUnitId;

	@Column
	private LocalDate checkInDate;

	@Column
	private LocalDate checkOutDate;

	@Column
	private String status;

	@Column
	private LocalDate requestTime;

}
