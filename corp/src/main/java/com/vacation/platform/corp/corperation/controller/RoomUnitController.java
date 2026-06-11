package com.vacation.platform.corp.corperation.controller;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corperation.dto.RoomDTO;
import com.vacation.platform.corp.corperation.service.RoomUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room/unit")
@RequiredArgsConstructor
public class RoomUnitController {

	private final RoomUnitService roomUnitService;

	@PostMapping("/create")
	public StayFinderResponseDTO<?>  createRoomUnit(@RequestHeader String token, @RequestBody RoomDTO roomDTO) {
		roomUnitService.createRoomUnit(token, roomDTO);
		return StayFinderResponseDTO.success();
	}

	@PostMapping("/modify")
	public StayFinderResponseDTO<?>  modifyRoomUnit(@RequestHeader String token, @RequestBody RoomDTO roomDTO) {
		roomUnitService.modifyRoomUnit(token, roomDTO);
		return StayFinderResponseDTO.success();
	}

}
