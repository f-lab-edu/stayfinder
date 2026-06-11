package com.vacation.platform.corp.reservation.repository;

import com.vacation.platform.corp.reservation.entity.ReservationRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRequestHistoryRepository extends JpaRepository<ReservationRequestHistory,Integer> {
}
