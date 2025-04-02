package com.vacation.platform.api.payment.entity;

import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_history")
@Setter
@Getter
@RequiredArgsConstructor
public class PaymentHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "reservation_id", nullable = false)
	private Long reservationId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "attempt_number")
	private Integer attemptNumber;

	public enum PaymentStatus {
		PENDING, SUCCESS, FAILED
	}
}
