package com.vacation.platform.batch.room_produce.config;

import com.vacation.platform.batch.room_produce.AutowiringSpringBeanJobFactory;
import com.vacation.platform.batch.room_produce.job.RoomStockJob;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Configuration
public class RoomStockConfig {

	@Bean
	public Trigger roomStockJobTrigger(JobDetail roomStockJobDetail) {
		return TriggerBuilder.newTrigger()
				.forJob(roomStockJobDetail)
				.withIdentity("RoomStockJobTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
				.build();
	}

	@Bean
	public JobDetail roomStockJobDetail() {
		return JobBuilder.newJob(RoomStockJob.class)
				.withIdentity("RoomStockJobJob")
				.storeDurably()
				.build();
	}

	@Bean
	public AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean roomStockJobSchedulerFactoryBean(
			AutowiringSpringBeanJobFactory jobFactory,
			JobDetail roomStockJobDetail,
			Trigger roomStockJobTrigger) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setJobDetails(roomStockJobDetail);
		factory.setTriggers(roomStockJobTrigger);
		return factory;
	}

}
