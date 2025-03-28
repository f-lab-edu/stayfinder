package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.room.RoomStockSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomStockSummaryRepository extends JpaRepository<RoomStockSummary, Long> {

	@Query("SELECT rts FROM RoomStockSummary rts WHERE rts.roomId = :roomId AND rts.stockDate = :stockDate")
	Optional<RoomStockSummary> findByRoomIdAndStockDate(Long roomId, LocalDate stockDate);

}
