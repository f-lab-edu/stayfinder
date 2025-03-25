package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.RoomProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomProductStockRepository extends JpaRepository<RoomProductStock, Long> {
}
