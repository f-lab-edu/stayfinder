package com.vacation.platform.api.payment.repository;

import com.vacation.platform.api.payment.entity.PaymentHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {
	@Query("SELECT ph FROM PaymentHistory ph WHERE ph.reservationId = :reservationId AND ph.userId = :userId AND ph.PaymentStatus not in ('SUCCESS')")
	Optional<PaymentHistory> findByReservationIdAndUserId(Long reservationId,  Long userId);
}
