package com.vacation.platform.stayfinder.login.service;

import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    ResponseEntity<StayFinderResponseDTO<?>> login(LoginDTO loginDTO);

}
