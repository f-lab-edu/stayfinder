package com.vacation.platform.corp.corperation.service;

import com.vacation.platform.corp.corperation.dto.RoomDTO;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {

	void createRoom(String token, RoomDTO roomDTO);

	void modifyRoom(String token, RoomDTO roomDTO);

}
