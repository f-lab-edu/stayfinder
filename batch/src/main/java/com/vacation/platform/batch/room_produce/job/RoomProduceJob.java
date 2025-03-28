package com.vacation.platform.batch.room_produce.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class RoomProduceJob implements Job{
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

	}
}
