package com.vacation.platform.api.payment.repository;

import com.vacation.platform.api.payment.entity.PaymentHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {
	@Query("SELECT ph FROM PaymentHistory ph WHERE ph.reservationId = :reservationId AND ph.userId = :userId AND ph.paymentStatus NOT IN ('SUCCESS')")
	Optional<PaymentHistory> findByReservationIdAndUserId(@Param("reservationId") Long reservationId, @Param("userId") Long userId);
}
