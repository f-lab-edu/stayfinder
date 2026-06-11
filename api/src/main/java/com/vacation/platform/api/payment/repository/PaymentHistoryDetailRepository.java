package com.vacation.platform.api.payment.repository;

import com.vacation.platform.api.payment.entity.PaymentHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PaymentHistoryDetailRepository extends JpaRepository<PaymentHistoryDetail, Long> {

	@Query("SELECT phd FROM PaymentHistoryDetail phd WHERE phd.reservationId = :reservationId AND phd.userId = :userId AND phd.amount = :amount")
	Optional<PaymentHistoryDetail> findByPaymentHistoryDetailId(Long reservationId, Long userId, BigDecimal amount);

}
