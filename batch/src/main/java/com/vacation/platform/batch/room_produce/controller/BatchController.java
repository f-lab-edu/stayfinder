package com.vacation.platform.batch.room_produce.controller;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.batch.room_produce.dto.BatchDTO;
import com.vacation.platform.batch.room_produce.job.RoomProduceJob;
import com.vacation.platform.batch.room_produce.service.BatchControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {
	private final BatchControlService batchControlService;
	private final RoomProduceJob roomProduceJob;

	@PostMapping("/room/produce")
	public StayFinderResponseDTO<?> roomProduceBatch(@RequestBody BatchDTO batchDTO) {
		boolean isAutoRunEnabled = batchControlService.isAutoRunEnabled(batchDTO.getBatchName());

		if(isAutoRunEnabled){
			try {
				roomProduceJob.execute(null);
			} catch (JobExecutionException e) {
				throw new StayFinderException(ErrorType.SYSTEM_ERROR, Map.of(), log::error, e);
			}
		} else {
			try {
				roomProduceJob.execute(null);
			} catch (JobExecutionException e) {
				throw new StayFinderException(ErrorType.SYSTEM_ERROR, Map.of(), log::error, e);
			}
		}
		return StayFinderResponseDTO.success();
	}


}
