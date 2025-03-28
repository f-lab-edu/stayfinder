package com.vacation.platform.batch.room_produce.service.impl;

import com.vacation.platform.batch.room_produce.entity.BatchControl;
import com.vacation.platform.batch.room_produce.repository.BatchControlRepository;
import com.vacation.platform.batch.room_produce.service.BatchControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchControlServiceImpl implements BatchControlService {

	private BatchControlRepository batchControlRepository;

	@Override
	public boolean isAutoRunEnabled(String batchName) {
		Optional<BatchControl> batchControl = batchControlRepository.findByBatchName(batchName);
		return batchControl.map(BatchControl::isAuto).orElse(false);
	}

	@Override
	public void updateLastRunTime(String batchName) {
		batchControlRepository.findByBatchName(batchName)
				.ifPresent(batchControl -> {
					batchControl.setLastRunTime(new java.sql.Timestamp(System.currentTimeMillis()));
					batchControlRepository.save(batchControl);
				});
	}

	@Override
	public void updateAutoRunSetting(String batchName, boolean isAuto) {
		batchControlRepository.findByBatchName(batchName).ifPresent(batchControl -> {
			batchControl.setAuto(isAuto);
			batchControlRepository.save(batchControl);
		});
	}
}
