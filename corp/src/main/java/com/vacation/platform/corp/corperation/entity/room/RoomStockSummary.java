package com.vacation.platform.corp.corperation.entity.room;

import com.vacation.platform.api.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "room_stock_summary")
@RequiredArgsConstructor
@Getter
@Setter
public class RoomStockSummary extends BaseEntity {

	@EmbeddedId
	private RoomStockSummaryId roomStockSummaryId;

	@Column(name = "total_stock", columnDefinition = "총 재고")
	private int totalStock;

	@Column(name = "available_stock", columnDefinition = "현재 재고")
	private int availableStock;
}
