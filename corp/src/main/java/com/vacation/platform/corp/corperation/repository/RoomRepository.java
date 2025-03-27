package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query("SELECT r FROM Room r WHERE r.corporation.corpUserId = :corpUserId")
	Optional<Room> findByRoomNumber(Long corpUserId, String roomNumber);

}
