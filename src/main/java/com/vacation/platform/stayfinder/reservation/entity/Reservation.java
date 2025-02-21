package com.vacation.platform.stayfinder.reservation.entity;

import com.vacation.platform.stayfinder.common.BaseEntity;
import com.vacation.platform.stayfinder.corpuser.entity.CorpUser;
import com.vacation.platform.stayfinder.corpuser.entity.Room;
import com.vacation.platform.stayfinder.user.entity.User;
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

	@ManyToOne(fetch = FetchType.LAZY)  // 필요할 때만 조회하도록 Lazy Loading 설정
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmed_by")
	private CorpUser confirmedBy;

	@Column(nullable = false)
	private Date reservationDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;
}