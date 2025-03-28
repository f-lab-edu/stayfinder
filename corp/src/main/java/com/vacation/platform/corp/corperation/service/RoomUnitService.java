package com.vacation.platform.corp.corperation.service;

import com.vacation.platform.corp.corperation.dto.RoomDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public interface RoomUnitService {
	void createRoomUnit(@RequestHeader String token, @RequestBody RoomDTO roomDTO);
	void modifyRoomUnit(@RequestHeader String token, @RequestBody RoomDTO roomDTO);
}
