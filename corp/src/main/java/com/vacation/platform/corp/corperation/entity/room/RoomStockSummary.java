package com.vacation.platform.corp.corperation.entity.room;

import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "room_stock_summary")
@IdClass(RoomStockSummaryId.class)
@RequiredArgsConstructor
@Getter
@Setter
public class RoomStockSummary extends BaseEntity {
	@Id
	@Column(name = "room_id")
	private Long roomId;

	@Id
	@Column(name = "stock_date")
	private LocalDate stockDate;

	@Column(name = "total_stock", columnDefinition = "총 재고")
	private int totalStock;

	@Column(name = "available_stock", columnDefinition = "현재 재고")
	private int availableStock;
}
