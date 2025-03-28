package com.vacation.platform.batch.room_produce.service;

import org.springframework.stereotype.Service;

@Service
public interface BatchControlService {
	boolean isAutoRunEnabled(String batchName);
	void updateLastRunTime(String batchName);
	void updateAutoRunSetting(String batchName, boolean isAuto);
}
