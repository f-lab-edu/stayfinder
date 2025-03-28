package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.entity.room.Room;
import com.vacation.platform.corp.corperation.repository.RoomRepository;
import com.vacation.platform.corp.corperation.service.RoomService;
import com.vacation.platform.corp.corperation.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	private final UtilService utilService;

	@Override
	@Transactional
	public void createRoom(String token, RoomDTO  roomDTO) {
		CorporateUser corporateUser = utilService.getCorporateUser(token);

		Room resultRoom = roomRepository.findByRoomType(roomDTO.getRoomType());
		if(resultRoom != null) {
			resultRoom.setCapacity(roomDTO.getCapacity());
			resultRoom.setRoomType(roomDTO.getRoomType());
			resultRoom.setPrice(roomDTO.getPrice());
			resultRoom.setCorporation(corporateUser.getCorporation());
		} else {
			Room room = new Room();
			room.setRoomType(roomDTO.getRoomType());
			room.setPrice(roomDTO.getPrice());
			room.setCapacity(roomDTO.getCapacity());
			room.setCorporation(corporateUser.getCorporation());
			roomRepository.save(room);
		}
	}


}
