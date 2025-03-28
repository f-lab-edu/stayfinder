package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.room.RoomStock;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomStockRepository extends JpaRepository<RoomStock, Long> {
	@Query("SELECT rs FROM RoomStock rs join RoomUnit ru ON ru.roomUnitId = rs.roomUnit.roomUnitId WHERE ru.roomUnitId = :roomUnitId AND rs.stockDate = :stockDate")
	Optional<RoomStock> findByRoomUnitIdAndStockDate(Long roomUnitId, LocalDate stockDate);

	@Query("SELECT COUNT(rs) FROM RoomStock rs JOIN RoomUnit ru ON rs.roomUnit.roomUnitId = ru.roomUnitId WHERE ru.room.roomId = :roomId AND rs.stockDate = :stockDate AND rs.reservation.reservationId IS NULL")
	Optional<Integer> countAvailableByRoomIdAndStockDate(@Param("roomId") Long roomId, @Param("stockDate") LocalDate stockDate);

}
