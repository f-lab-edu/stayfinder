package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.corp.corperation.entity.CorpStatus;
import com.vacation.platform.corp.corperation.entity.Corporation;
import com.vacation.platform.corp.corperation.entity.room.*;
import com.vacation.platform.corp.corperation.repository.*;
import com.vacation.platform.corp.corperation.service.RoomStockService;
import com.vacation.platform.corp.reservation.dto.ReservationDTO;
import com.vacation.platform.corp.reservation.entity.Reservation;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RoomStockServiceImpl implements RoomStockService {

	private final CorporationRepository corporationRepository;
	private final RoomRepository roomRepository;
	private final RoomUnitRepository roomUnitRepository;
	private final RoomStockRepository roomStockRepository;
	private final RoomStockSummaryRepository roomStockSummaryRepository;
	private final RedissonClient redissonClient;

	public  RoomStockServiceImpl(CorporationRepository corporationRepository, RoomRepository roomRepository,
	                             RoomUnitRepository roomUnitRepository, RoomStockRepository roomStockRepository,
	                             RoomStockSummaryRepository roomStockSummaryRepository) {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://localhost:6379");

		this.redissonClient = Redisson.create(config);
		this.corporationRepository = corporationRepository;
		this.roomRepository = roomRepository;
		this.roomUnitRepository = roomUnitRepository;
		this.roomStockRepository = roomStockRepository;
		this.roomStockSummaryRepository = roomStockSummaryRepository;
	}

	@Override
	@Transactional
	public void generateTodayRoomStock() {
		LocalDate today = LocalDate.now();
		List<Corporation> corporationList = corporationRepository.findByCorpStatus(CorpStatus.REGISTERED);
		// find 후 인서트
		// 건건이 인서트 하지 말고 모아서 인서트
		// 재실행시 오류 없게
		for (Corporation corp : corporationList) {
			List<Room> roomList = roomRepository.findByRoom(corp.getCorpUserId());
			for (Room room : roomList) {
				List<RoomUnit> roomUnitList = roomUnitRepository.findByRoomId(room.getRoomId());
				List<RoomStock>  roomStockList = new ArrayList<>();
				for(RoomUnit roomUnit : roomUnitList){

					RoomStock roomStock = roomStockRepository.findByRoomUnitIdAndStockDate(roomUnit.getRoomUnitId(), today)
							.orElseGet(() -> {
								RoomStock newStock = new RoomStock();
								newStock.setRoomUnit(roomUnit);
								newStock.setStockDate(today);
								newStock.setReservation(null);
								return newStock;
							});
					roomStockList.add(roomStock);
				}
				if (!roomStockList.isEmpty()) {
					roomStockRepository.saveAll(roomStockList);
				}
				List<RoomStockSummary> roomStockSummaryList = new ArrayList<>();
				int totalStock = roomUnitList.size();
				int availableStock = roomStockRepository.countAvailableByRoomIdAndStockDate(room.getRoomId(), today)
						.orElseGet(() -> {return 0;});

				RoomStockSummary roomStockSummary = roomStockSummaryRepository.findByRoomIdAndStockDate(room.getRoomId(), today)
						.orElseGet(() -> null);

				if(roomStockSummary ==  null){
					roomStockSummary = new RoomStockSummary();
					RoomStockSummaryId roomStockSummaryId = new RoomStockSummaryId();
					roomStockSummaryId.setRoomId(room.getRoomId());
					roomStockSummaryId.setStockDate(today);
					roomStockSummary.setRoomStockSummaryId(roomStockSummaryId);
					roomStockSummary.setTotalStock(totalStock);
					roomStockSummary.setAvailableStock(availableStock);
					roomStockSummaryList.add(roomStockSummary);
				} else {
					roomStockSummary.setTotalStock(totalStock);
					roomStockSummary.setAvailableStock(availableStock);
				}
				if (!roomStockSummaryList.isEmpty()){
					roomStockSummaryRepository.saveAll(roomStockSummaryList);
				}
			}
		}

	}

	@Override
	public Map<LocalDate, RoomStock> allocateRoomStocks(ReservationDTO reservationDTO) {
		List<RoomUnit> roomUnits = roomUnitRepository.findByRoomId(reservationDTO.getRoomId());
		Map<LocalDate, RoomStock> allocationMap = new HashMap<>();

		for (RoomUnit roomUnit : roomUnits) {
			Boolean candidateAvailable = true;
			List<RoomStock>  roomStockList = new ArrayList<>();
			for (LocalDate date = reservationDTO.getCheckIn(); date.isBefore(reservationDTO.getCheckOut()); date = date.plusDays(1)) {
				Optional<RoomStock> stockOpt = roomStockRepository
						.findByRoomUnitIdAndStockDateAndReservationIdIsNull(roomUnit.getRoomUnitId(), date);
				if (stockOpt.isEmpty()) {
					candidateAvailable = false;
					break;
				}
				roomStockList.add(stockOpt.get());
			}
			if (candidateAvailable) {
				boolean allLocked = true;
				List<RLock> acquiredLocks = new ArrayList<>();
				try {
					for (RoomStock stock : roomStockList) {
						String lockKey = "lock:room_stock:" + stock.getRoomStockId();
						RLock lock = redissonClient.getLock(lockKey);
						if (lock.tryLock(10, TimeUnit.SECONDS)) {
							acquiredLocks.add(lock);
						} else {
							allLocked = false;
							break;
						}
					}
					if (!allLocked) {
						for (RLock lock : acquiredLocks) {
							lock.unlock();
						}
						continue;
					}

					boolean recheckPassed = true;
					for (RoomStock stock : roomStockList) {
						Optional<RoomStock> recheckOpt = roomStockRepository.findById(stock.getRoomStockId());
						if (recheckOpt.isEmpty() || recheckOpt.get().getReservation() != null) {
							recheckPassed = false;
							break;
						}
					}
					if (!recheckPassed) {
						for (RLock lock : acquiredLocks) {
							lock.unlock();
						}
						continue;
					}

					for (RoomStock stock : roomStockList) {
						Reservation reservation = stock.getReservation();
						reservation.setReservationId(-1L);
						roomStockRepository.save(stock);
						allocationMap.put(stock.getStockDate(), stock);
					}
					for (RLock lock : acquiredLocks) {
						lock.unlock();
					}

					return allocationMap;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException("RoomStock 락 획득 중 인터럽트 발생, roomUnitId: " + roomUnit.getRoomUnitId(), e);
				}
			}

			for (LocalDate date = reservationDTO.getCheckIn(); date.isBefore(reservationDTO.getCheckOut()); date = date.plusDays(1)) {
				boolean allocated = false;
				for (RoomUnit unit : roomUnits) {
					Optional<RoomStock> stockOpt = roomStockRepository
							.findByRoomUnitIdAndStockDateAndReservationIdIsNull(unit.getRoomUnitId(), date);
					if (stockOpt.isPresent()) {
						RoomStock stock = stockOpt.get();
						String lockKey = "lock:room_stock:" + stock.getRoomStockId();
						RLock lock = redissonClient.getLock(lockKey);
						try {
							if (lock.tryLock(10, TimeUnit.SECONDS)) {
								try {
									Optional<RoomStock> recheckOpt = roomStockRepository.findById(stock.getRoomStockId());
									if (recheckOpt.isPresent() && recheckOpt.get().getReservation().getReservationId() == null) {
										RoomStock availableStock = recheckOpt.get();
										Reservation reservation = availableStock.getReservation();
										reservation.setReservationId(-1L);
										availableStock.setReservation(reservation);
										roomStockRepository.save(availableStock);
										allocationMap.put(date, availableStock);
										allocated = true;
										break;
									}
								} finally {
									lock.unlock();
								}
							}
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							throw new RuntimeException("RoomStock 락 획득 중 인터럽트 발생, roomStockId: " + stock.getRoomStockId(), e);
						}
					}
				}
				if (!allocated) {
					throw new RuntimeException("예약 가능한 재고가 없습니다. 날짜: " + date);
				}
			}


		}
		return allocationMap;
	}

}
