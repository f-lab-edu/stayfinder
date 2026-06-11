package com.vacation.platform.api.payment.controller;

import com.vacation.platform.api.payment.dto.PaymentHistoryDTO;
import com.vacation.platform.api.payment.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

	private final PaymentHistoryService paymentHistoryService;

	@PostMapping("callback")
	public ResponseEntity<?> paymentCallBack(PaymentHistoryDTO paymentHistoryDTO) {
		paymentHistoryService.processPaymentCallback(paymentHistoryDTO);
		return ResponseEntity.ok("ok");
	}

}
