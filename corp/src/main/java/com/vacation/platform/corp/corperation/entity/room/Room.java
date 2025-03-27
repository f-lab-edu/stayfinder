package com.vacation.platform.corp.corperation.entity.room;

import com.vacation.platform.api.common.BaseEntity;
import com.vacation.platform.corp.corperation.entity.Corporation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "room")
@Data
@RequiredArgsConstructor
public class Room extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, updatable = false)
	private Long roomId;

	@Column(nullable = false)
	private String name;

	@Column(name = "room_type", nullable = false)
	private String roomType;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer capacity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "corp_user_id", nullable = false)
	private Corporation corporation;

	@OneToMany(mappedBy = "room")
	private List<RoomUnit> roomStocks;
}