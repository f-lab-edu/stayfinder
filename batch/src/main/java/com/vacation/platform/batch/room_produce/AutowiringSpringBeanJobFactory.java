package com.vacation.platform.batch.room_produce;

import org.jetbrains.annotations.NotNull;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware{
	private AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		beanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	@NotNull
	@Override
	protected Object createJobInstance(@NotNull TriggerFiredBundle bundle) throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		// 생성된 Job 인스턴스에 대해 의존성 자동 주입 수행
		beanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
