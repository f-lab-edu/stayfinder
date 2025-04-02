package com.vacation.platform.api.payment.dto;

import com.vacation.platform.api.payment.entity.PaymentMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@RequiredArgsConstructor
public class PaymentHistoryDTO {
	private Long reservationId;
	private Long userId;
	private BigDecimal amount;
	private String status;
	private PaymentMethod paymentMethod;
}
