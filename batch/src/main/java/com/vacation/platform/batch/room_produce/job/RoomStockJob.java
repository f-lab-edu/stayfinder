package com.vacation.platform.batch.room_produce.job;

import com.vacation.platform.corp.corperation.service.RoomStockService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoomStockJob implements Job {

	@Autowired
	private RoomStockService roomStockService;

	// 기본 생성자만 사용
	public RoomStockJob() {
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		log.info("RoomProduceJob 실행 시간 {}", System.currentTimeMillis());
		roomStockService.generateTodayRoomStock();
	}
}
