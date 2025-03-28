package com.vacation.platform.batch.room_produce.repository;

import com.vacation.platform.batch.room_produce.entity.BatchControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchControlRepository extends JpaRepository<BatchControl,Long> {
	Optional<BatchControl> findByBatchName(String batchName);
}
