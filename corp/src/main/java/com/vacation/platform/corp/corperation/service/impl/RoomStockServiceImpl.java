package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.corp.corperation.entity.CorpStatus;
import com.vacation.platform.corp.corperation.entity.Corporation;
import com.vacation.platform.corp.corperation.entity.room.Room;
import com.vacation.platform.corp.corperation.entity.room.RoomStock;
import com.vacation.platform.corp.corperation.entity.room.RoomStockSummary;
import com.vacation.platform.corp.corperation.entity.room.RoomUnit;
import com.vacation.platform.corp.corperation.repository.*;
import com.vacation.platform.corp.corperation.service.RoomStockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomStockServiceImpl implements RoomStockService {

	private final CorporationRepository corporationRepository;
	private final RoomRepository roomRepository;
	private final RoomUnitRepository roomUnitRepository;
	private final RoomStockRepository roomStockRepository;
	private final RoomStockSummaryRepository roomStockSummaryRepository;

	@Override
	@Transactional
	public void generateTodayRoomStock() {
		LocalDate today = LocalDate.now();
		List<Corporation> corporationList = corporationRepository.findByCorpStatus(CorpStatus.REGISTERED);

		for (Corporation corp : corporationList) {
			List<Room> roomList = roomRepository.findByRoom(corp.getCorpUserId());
			for (Room room : roomList) {
				List<RoomUnit> roomUnitList = roomUnitRepository.findByRoomId(room.getRoomId());

				for(RoomUnit roomUnit : roomUnitList){
					roomStockRepository.findByRoomUnitIdAndStockDate(roomUnit.getRoomUnitId(), today)
							.orElseGet(() -> {
								RoomStock newStock = new RoomStock();
								newStock.setRoomUnit(roomUnit);
								newStock.setStockDate(today);
								newStock.setReservation(null);
								return roomStockRepository.save(newStock);
							});
				}
				int totalStock = roomUnitList.size();
				int availableStock = roomStockRepository.countAvailableByRoomIdAndStockDate(room.getRoomId(), today)
						.orElseGet(() -> {return 0;});

				RoomStockSummary roomStockSummary = roomStockSummaryRepository.findByRoomIdAndStockDate(room.getRoomId(), today)
						.orElseGet(() -> null);

				if(roomStockSummary ==  null){
					roomStockSummary = new RoomStockSummary();
					roomStockSummary.setRoomId(room.getRoomId());
					roomStockSummary.setStockDate(today);
					roomStockSummary.setTotalStock(totalStock);
					roomStockSummary.setAvailableStock(availableStock);
				} else {
					roomStockSummary.setTotalStock(totalStock);
					roomStockSummary.setAvailableStock(availableStock);
				}
				roomStockSummaryRepository.save(roomStockSummary);
			}
		}

	}


}
