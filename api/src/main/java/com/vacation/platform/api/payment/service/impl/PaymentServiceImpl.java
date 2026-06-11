package com.vacation.platform.api.payment.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.payment.dto.PaymentHistoryDTO;
import com.vacation.platform.api.payment.entity.PaymentHistory;
import com.vacation.platform.api.payment.entity.PaymentHistoryDetail;
import com.vacation.platform.api.payment.repository.PaymentHistoryDetailRepository;
import com.vacation.platform.api.payment.repository.PaymentHistoryRepository;
import com.vacation.platform.api.payment.service.PaymentHistoryService;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentHistoryService {

	private final PaymentHistoryRepository paymentHistoryRepository;

	private final PaymentHistoryDetailRepository paymentHistoryDetailRepository;

	@Override
	public StayFinderResponseDTO<?> processPaymentCallback(PaymentHistoryDTO paymentHistoryDTO) {
		if(Objects.equals(paymentHistoryDTO.getStatus(), "SUCCESS")) {
			paymentHistoryDetailRepository.findByPaymentHistoryDetailId(paymentHistoryDTO.getReservationId(), paymentHistoryDTO.getUserId(), paymentHistoryDTO.getAmount())
					.orElseThrow( () -> new StayFinderException(ErrorType.SYSTEM_ERROR, Map.of(), log::error));
		} else {
			PaymentHistoryDetail paymentHistoryDetail = new PaymentHistoryDetail();
			paymentHistoryDetail.setReservationId(paymentHistoryDTO.getReservationId());
			paymentHistoryDetail.setUserId(paymentHistoryDTO.getUserId());
			paymentHistoryDetail.setAmount(paymentHistoryDTO.getAmount());
			paymentHistoryDetail.setPaymentMethod(paymentHistoryDTO.getPaymentMethod());
			paymentHistoryDetailRepository.save(paymentHistoryDetail);
		}

		return StayFinderResponseDTO.success();
	}

	@Override
	public StayFinderResponseDTO<?> requestPayment(PaymentHistoryDTO paymentHistoryDTO) {

		PaymentHistory resultPaymentHistory = paymentHistoryRepository.findByReservationIdAndUserId(paymentHistoryDTO.getReservationId(), paymentHistoryDTO.getUserId())
				.orElseGet(() -> {
					PaymentHistory paymentHistory = new PaymentHistory();
					paymentHistory.setReservationId(paymentHistoryDTO.getReservationId());
					paymentHistory.setUserId(paymentHistoryDTO.getUserId());
					paymentHistory.setAmount(paymentHistoryDTO.getAmount());
					paymentHistory.setPaymentStatus(PaymentHistory.PaymentStatus.PENDING);
					paymentHistory.setPaymentMethod(paymentHistoryDTO.getPaymentMethod());
					return paymentHistoryRepository.save(paymentHistory);
				});

		resultPaymentHistory.setAttemptNumber(resultPaymentHistory.getAttemptNumber() + 1);

		try {
			// 결제 요청 처리
			log.info("결제 요청");
		} catch (Exception e) {
			log.error(e.getMessage());
			StayFinderResponseDTO.success("Failed");
		}

		return StayFinderResponseDTO.success();
	}


}
