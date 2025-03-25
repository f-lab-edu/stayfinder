package com.vacation.platform.corp.corperation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDTO {
	private String name;
	private String roomNumber;
	private String roomType;
	private BigDecimal price;
	private Integer capacity;
	private String roomTypeName;
}