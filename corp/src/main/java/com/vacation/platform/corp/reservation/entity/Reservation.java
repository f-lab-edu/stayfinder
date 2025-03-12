package com.vacation.platform.corp.reservation.entity;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.user.entity.User;
import com.vacation.platform.corp.corpuser.entity.CorporateUser;
import com.vacation.platform.corp.corpuser.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
@Entity
@RequiredArgsConstructor
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, updatable = false)
	private Long reservationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Date reservationDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmed_by")
	private CorporateUser confirmedBy;
}