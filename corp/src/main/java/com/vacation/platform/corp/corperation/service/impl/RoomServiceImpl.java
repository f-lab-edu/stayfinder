package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.JwtUtil;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.entity.room.Room;
import com.vacation.platform.corp.corperation.repository.CorporateUserRepository;
import com.vacation.platform.corp.corperation.repository.RoomRepository;
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

	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public void createRoom(String token, RoomDTO  roomDTO) {

		CorporateUser corporateUser = getCorporateUser(token);

		roomRepository.findByRoomNumber(corporateUser.getId(), roomDTO.getRoomNumber()).ifPresent(
				room -> {
					throw new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error);
				}
		);

		Room room = new Room();
		room.setName(roomDTO.getName());
		room.setRoomType(roomDTO.getRoomType());
		room.setPrice(roomDTO.getPrice());
		room.setCapacity(roomDTO.getCapacity());
		room.setCorporation(corporateUser.getCorporation());

		roomRepository.save(room);
	}

	@Override
	public void modifyRoom(String token, RoomDTO roomDTO) {
		CorporateUser corporateUser = getCorporateUser(token);

		Room room = roomRepository.findByRoomNumber(corporateUser.getId(), roomDTO.getRoomNumber()).orElseThrow(
				() -> new StayFinderException(ErrorType.ROOM_NUMBER_IS_NOT_EXIST, Map.of("roomNumber", roomDTO.getRoomNumber()), log::error));

		room.setName(roomDTO.getName());
		room.setRoomType(roomDTO.getRoomType());
		room.setPrice(roomDTO.getPrice());
		room.setCapacity(roomDTO.getCapacity());
	}

	private CorporateUser  getCorporateUser(String token) {
		String email = jwtUtil.getUserEmail(token);
		return corporateUserRepository.findByEmail(email).orElseThrow(
				() -> new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error)
		);
	}
}
