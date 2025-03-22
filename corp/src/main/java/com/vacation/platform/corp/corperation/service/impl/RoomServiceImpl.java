package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.util.JwtUtil;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.entity.Room;
import com.vacation.platform.corp.corperation.entity.RoomType;
import com.vacation.platform.corp.corperation.repository.CorporateUserRepository;
import com.vacation.platform.corp.corperation.repository.RoomRepository;
import com.vacation.platform.corp.corperation.repository.RoomTypeRepository;
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

	private final RoomTypeRepository roomTypeRepository;

	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public void createRoom(String token, RoomDTO  roomDTO) {

		String email = jwtUtil.getUserEmail(token);

		if(email == null) {
			throw new StayFinderException(ErrorType.USER_EMAIL_NOT_EXIST, Map.of("error", "Email not exist"), log::error);
		}

		CorporateUser corporateUser =  corporateUserRepository.findByEmail(email).orElseThrow(
				() -> new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error)
		);

		roomRepository.findByRoomNumber(corporateUser.getId(), roomDTO.getRoomNumber()).ifPresent(
				room -> {
					throw new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error);
				}
		);

		RoomType roomType = roomTypeRepository.findByRoomTypeName(roomDTO.getRoomTypeName())
				.orElseThrow(
						() -> new StayFinderException(ErrorType.BUSINESS_IS_NOT_EXIST, Map.of(), log::error));

		Room room = new Room();
		room.setRoom_number(roomDTO.getRoomNumber());
		room.setRoomType(roomType);
		room.setPrice(roomDTO.getPrice());
		room.setCapacity(roomDTO.getCapacity());
		room.setCorporation(corporateUser.getCorporation());

		roomRepository.save(room);
	}
}
