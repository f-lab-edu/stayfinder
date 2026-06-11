package com.vacation.platform.corp.corperation.entity.room;


import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
		name = "room_unit",
		uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "room_name"})
)
@Data
@RequiredArgsConstructor
public class RoomUnit extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_unit_id")
	private Long roomUnitId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(name = "room_name", nullable = false, length = 50)
	private String roomName;

}
