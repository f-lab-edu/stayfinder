package com.vacation.platform.corp.reservation.repository;

import com.vacation.platform.corp.reservation.entity.ReservationRoomStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRoomStockRepository extends JpaRepository<ReservationRoomStock, Integer> {
}
