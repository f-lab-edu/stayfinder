package com.vacation.platform.corp.reservation.service;

import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.reservation.dto.ReservationDTO;
import org.springframework.stereotype.Service;


@Service
public interface ReservationService {
	StayFinderResponseDTO<?> createReservation(String token, ReservationDTO reservationDTO);
}
