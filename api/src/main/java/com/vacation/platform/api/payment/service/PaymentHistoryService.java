package com.vacation.platform.api.payment.service;

import com.vacation.platform.api.payment.dto.PaymentHistoryDTO;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentHistoryService {
	StayFinderResponseDTO<?> processPaymentCallback(PaymentHistoryDTO paymentHistoryDTO);

	StayFinderResponseDTO<?> requestPayment(PaymentHistoryDTO paymentHistoryDTO);

}
