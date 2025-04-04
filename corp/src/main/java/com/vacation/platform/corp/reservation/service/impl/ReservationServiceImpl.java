package com.vacation.platform.corp.reservation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.payment.dto.PaymentHistoryDTO;
import com.vacation.platform.api.payment.service.impl.PaymentServiceImpl;
import com.vacation.platform.api.user.entity.User;
import com.vacation.platform.api.user.repository.UserRepository;
import com.vacation.platform.api.util.JwtUtil;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corperation.entity.room.RoomStock;
import com.vacation.platform.corp.corperation.service.RoomStockService;
import com.vacation.platform.corp.reservation.dto.ReservationDTO;
import com.vacation.platform.corp.reservation.entity.ReservationRequestHistory;
import com.vacation.platform.corp.reservation.entity.ReservationRoomStock;
import com.vacation.platform.corp.reservation.repository.ReservationRequestHistoryRepository;
import com.vacation.platform.corp.reservation.repository.ReservationRoomStockRepository;
import com.vacation.platform.corp.reservation.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

	private final RoomStockService  roomStockService;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PaymentServiceImpl paymentService;
	private final ReservationRequestHistoryRepository reservationRequestHistoryRepository;
	private final ReservationRoomStockRepository reservationRoomStockRepository;

	public ReservationServiceImpl(RoomStockService roomStockService,  JwtUtil jwtUtil, UserRepository userRepository,
	                              PaymentServiceImpl paymentService, ReservationRequestHistoryRepository reservationRequestHistoryRepository,
	                              ReservationRoomStockRepository reservationRoomStockRepository) {

		this.roomStockService = roomStockService;
		this.jwtUtil =  jwtUtil;
		this.userRepository = userRepository;
		this.paymentService = paymentService;
		this.reservationRequestHistoryRepository = reservationRequestHistoryRepository;
		this.reservationRoomStockRepository = reservationRoomStockRepository;
	}

	@Override
	@Transactional
	public StayFinderResponseDTO<?> createReservation(String token, ReservationDTO reservationDTO) {
		long reservationDays = ChronoUnit.DAYS.between(reservationDTO.getCheckIn(), reservationDTO.getCheckOut());
		if (reservationDays > 30) {
			throw new StayFinderException(ErrorType.ROOM_RESERVATION_30, Map.of("Date", reservationDays), log::error);
		}

		Map<LocalDate, RoomStock> allocationMap = roomStockService.allocateRoomStocks(reservationDTO);

		User user = userRepository.findByEmail(jwtUtil.getUserEmail(token))
				.orElseThrow( () -> new StayFinderException(ErrorType.USER_NOT_FOUND, Map.of("error", "User not found"),
						log::error));

		ReservationRequestHistory requestHistory = new ReservationRequestHistory();
		requestHistory.setUserId(user.getUserId());
		requestHistory.setRoomId(reservationDTO.getRoomId());

		RoomStock firstStock = allocationMap.values().iterator().next();
		requestHistory.setRoomUnitId(firstStock.getRoomUnit().getRoomUnitId());
		requestHistory.setCheckInDate(reservationDTO.getCheckIn());
		requestHistory.setCheckOutDate(reservationDTO.getCheckOut());
		requestHistory.setStatus("PENDING");
		requestHistory.setRequestTime(LocalDate.from(LocalDateTime.now()));
		reservationRequestHistoryRepository.save(requestHistory);

		for (Map.Entry<LocalDate, RoomStock> entry : allocationMap.entrySet()) {
			ReservationRoomStock mapping = new ReservationRoomStock();
			mapping.getReservation().setReservationId(null);
			mapping.getRoomStock().setRoomStockId(entry.getValue().getRoomStockId());
			mapping.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			reservationRoomStockRepository.save(mapping);
		}

		PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();

		paymentHistoryDTO.setAmount(reservationDTO.getAmount());
		paymentHistoryDTO.setPaymentMethod(reservationDTO.getPayMethod());
		paymentHistoryDTO.setReservationId(null);
		paymentHistoryDTO.setUserId(user.getUserId());

		paymentService.requestPayment(paymentHistoryDTO);

		return StayFinderResponseDTO.success();
	}
}
