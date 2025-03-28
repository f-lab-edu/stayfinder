package com.vacation.platform.batch.room_produce.config;

import com.vacation.platform.batch.room_produce.job.RoomProduceJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Configuration
public class RoomProduceConfig {

	@Bean
	public JobDetail roomProduceJobDetail() {
		return JobBuilder.newJob(RoomProduceJob.class)
				.withIdentity("RoomProduceJob")
				.storeDurably()
				.build();
	}

	@Bean
	public Trigger roomProduceJobTrigger(JobDetail roomProduceJobDetail) {
		return TriggerBuilder.newTrigger()
				.forJob(roomProduceJobDetail)
				.withIdentity("roomProduceJobTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 * 1/1 * ? *"))  // 10분마다 실행
				.build();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(Trigger roomProduceJobTrigger, JobDetail roomProduceJobDetail) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobDetails(roomProduceJobDetail);
		schedulerFactoryBean.setTriggers(roomProduceJobTrigger);
		return schedulerFactoryBean;
	}

}
