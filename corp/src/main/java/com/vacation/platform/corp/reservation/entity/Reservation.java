package com.vacation.platform.corp.reservation.entity;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.api.user.entity.User;
import com.vacation.platform.corp.corpuser.entity.CorporateUser;
import com.vacation.platform.corp.corpuser.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
@Entity
@RequiredArgsConstructor
public class Reservation extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reservationId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(name = "reservation_date", nullable = false)
	private LocalDate reservationDate;

	@Column(nullable = false)
	private String status = "PENDING";

	@ManyToOne
	@JoinColumn(name = "confirmed_by")
	private CorporateUser confirmedBy;
}