package com.vacation.platform.corp.reservation.controller;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.reservation.dto.ReservationDTO;
import com.vacation.platform.corp.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/create")
	public StayFinderResponseDTO<?> createReservation(@RequestHeader String token, @RequestBody ReservationDTO reservationDTO) {
		return reservationService.createReservation(token, reservationDTO);
	}

}
