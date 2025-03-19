package com.vacation.platform.corp.corperation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_type")
@Data
@RequiredArgsConstructor
public class RoomType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomTypeId;

	@Column
	private String roomTypeName;

	@Column
	private String description;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createAt;
}
