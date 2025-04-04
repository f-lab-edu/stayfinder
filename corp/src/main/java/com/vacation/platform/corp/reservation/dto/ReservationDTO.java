package com.vacation.platform.corp.reservation.dto;

import com.vacation.platform.api.payment.entity.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class ReservationDTO {
	private Long roomId;

	private Long roomUnitId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate checkIn;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate checkOut;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate stockDate;

	private BigDecimal amount;

	private PaymentMethod payMethod;
}
