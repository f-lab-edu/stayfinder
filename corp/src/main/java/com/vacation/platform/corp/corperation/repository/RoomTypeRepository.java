package com.vacation.platform.corp.corperation.repository;

import com.vacation.platform.corp.corperation.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

	@Query("SELECT rt FROM RoomType rt WHERE rt.roomTypeName = :roomTypeName")
	Optional<RoomType> findByRoomTypeName(String roomTypeName);
}
