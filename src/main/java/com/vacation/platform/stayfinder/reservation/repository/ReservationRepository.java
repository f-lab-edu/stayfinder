package com.vacation.platform.stayfinder.reservation.repository;

import com.vacation.platform.stayfinder.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}