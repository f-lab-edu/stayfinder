package com.vacation.platform.corp.corperation.controller;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService  roomService;

	@PostMapping("/create")
	public StayFinderResponseDTO<?> createRoom(@RequestHeader("Authorization") String token, @RequestBody RoomDTO roomDTO){
		roomService.createRoom(token, roomDTO);
		return StayFinderResponseDTO.success();
	}

}