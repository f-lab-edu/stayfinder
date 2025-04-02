package com.vacation.platform.api.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PaymentResultDTO {
	private PaymentHistoryDTO paymentHistoryDTO;
}
