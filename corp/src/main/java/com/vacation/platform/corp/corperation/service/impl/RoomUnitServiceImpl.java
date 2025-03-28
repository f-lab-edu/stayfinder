package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.entity.room.Room;
import com.vacation.platform.corp.corperation.entity.room.RoomUnit;
import com.vacation.platform.corp.corperation.repository.RoomRepository;
import com.vacation.platform.corp.corperation.repository.RoomUnitRepository;
import com.vacation.platform.corp.corperation.service.RoomUnitService;
import com.vacation.platform.corp.corperation.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomUnitServiceImpl implements RoomUnitService {

	private final RoomUnitRepository roomUnitRepository;

	private final RoomRepository roomRepository;

	private final UtilService utilService;

	@Override
	public void createRoomUnit(String token, RoomDTO roomDTO) {
		CorporateUser corporateUser = utilService.getCorporateUser(token);

		Room room = getRoom(roomDTO);

		roomUnitRepository.findByRoomName(roomDTO.getRoomName(), corporateUser.getId())
				.ifPresent(roomUnit -> {
						throw new StayFinderException(ErrorType.ROOM_NAME_IS_EXIST,  Map.of("roomName", roomDTO.getRoomName()), log::error);
				});

		RoomUnit roomUnit = new RoomUnit();
		roomUnit.setRoomName(roomDTO.getRoomName());
		roomUnit.setRoom(room);
		roomUnitRepository.save(roomUnit);
	}

	@Override
	public void modifyRoomUnit(String token, RoomDTO roomDTO) {
		CorporateUser corporateUser = utilService.getCorporateUser(token);

		getRoom(roomDTO);

		RoomUnit roomUnit = roomUnitRepository.findByRoomName(roomDTO.getRoomName(), corporateUser.getId())
				.orElseThrow(() -> new StayFinderException(ErrorType.ROOM_NAME_IS_NOT_EXIST, Map.of("roomName", roomDTO.getRoomName()), log::error));

		roomUnit.setRoomName(roomDTO.getRoomName());
	}

	private Room getRoom(RoomDTO roomDTO) {
		Room room = roomRepository.findByRoomType(roomDTO.getRoomType());

		if (room == null) {
			throw new StayFinderException(ErrorType.ROOM_TYPE_NAME_IS_NOT_EXIST, Map.of("roomType", roomDTO.getRoomTypeName()), log::error);
		}
		return room;
	}
}
