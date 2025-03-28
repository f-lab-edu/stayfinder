package com.vacation.platform.corp.corperation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDTO {
	private String roomName;
	private String roomType;
	private BigDecimal price;
	private Integer capacity;
	private String roomTypeName;
}