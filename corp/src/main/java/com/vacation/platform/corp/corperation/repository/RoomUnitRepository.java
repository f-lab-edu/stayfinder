package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.room.RoomUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomUnitRepository extends JpaRepository<RoomUnit, Long> {

	@Query("SELECT rc FROM RoomUnit rc JOIN Room r ON r.roomId = rc.room.roomId WHERE r.corporation.corpUserId = :corpUserId  AND rc.roomName = :roomName")
	Optional<RoomUnit> findByRoomName(String roomName, Long corpUserId );
}
