package com.vacation.platform.corp.corperation.entity.room;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RoomStockSummaryId implements Serializable {
	private Long roomId;
	private LocalDate stockDate;
}
