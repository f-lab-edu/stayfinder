package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.JwtUtil;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.entity.room.Room;
import com.vacation.platform.corp.corperation.entity.room.RoomUnit;
import com.vacation.platform.corp.corperation.repository.CorporateUserRepository;
import com.vacation.platform.corp.corperation.repository.RoomRepository;
import com.vacation.platform.corp.corperation.repository.RoomUnitRepository;
import com.vacation.platform.corp.corperation.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	private final CorporateUserRepository corporateUserRepository;

	private final RoomUnitRepository roomUnitRepository;

	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public void createRoom(String token, RoomDTO  roomDTO) {
		CorporateUser corporateUser = getCorporateUser(token);

		roomRepository.findByRoomNumber(corporateUser.getId()).ifPresent(
				room -> {
					throw new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error);
				}
		);

		roomUnitRepository.findByRoomName(roomDTO.getRoomNumber(), corporateUser.getId())
				.ifPresent( roomUnit -> {
					throw new StayFinderException(ErrorType.ROOM_NUMBER_IS_EXIST,
							Map.of("roomNumber", roomDTO.getRoomNumber()), log::error);
				});

		Room room = new Room();
		room.setRoomType(roomDTO.getRoomType());
		room.setPrice(roomDTO.getPrice());
		room.setCapacity(roomDTO.getCapacity());
		room.setCorporation(corporateUser.getCorporation());
		roomRepository.saveAndFlush(room);

		RoomUnit roomUnit = new RoomUnit();
		roomUnit.setRoomName(roomDTO.getRoomNumber());
		roomUnit.setRoom(room);
		roomUnitRepository.saveAndFlush(roomUnit);
	}

	@Override
	public void modifyRoom(String token, RoomDTO roomDTO) {
		CorporateUser corporateUser = getCorporateUser(token);

		RoomUnit roomUnit = roomUnitRepository.findByRoomName(roomDTO.getRoomNumber(), corporateUser.getId())
				.orElseThrow(() -> new StayFinderException(ErrorType.ROOM_NUMBER_IS_NOT_EXIST, Map.of("roomNumber", roomDTO.getRoomNumber()), log::error));

		Room room = roomRepository.findByRoomId(roomUnit.getRoom().getRoomId())
						.orElseThrow( () -> new StayFinderException(ErrorType.ROOM_IS_NOT_EXIST, Map.of("roomId", roomUnit.getRoom().getRoomId()), log::error));

		room.setRoomType(roomDTO.getRoomType());
		room.setPrice(roomDTO.getPrice());
		room.setCapacity(roomDTO.getCapacity());

		roomUnit.setRoom(room);
		roomUnit.setRoomName(roomDTO.getRoomNumber());
	}

	private CorporateUser  getCorporateUser(String token) {
		String email = jwtUtil.getUserEmail(token);
		return corporateUserRepository.findByEmail(email).orElseThrow(
				() -> new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error)
		);
	}
}
