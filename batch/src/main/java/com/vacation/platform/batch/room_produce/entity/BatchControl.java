package com.vacation.platform.batch.room_produce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
public class BatchControl {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String batchName;
	private boolean isAuto;  // 자동 실행 여부
	private Timestamp lastRunTime;  // 마지막 실행 시간
}
