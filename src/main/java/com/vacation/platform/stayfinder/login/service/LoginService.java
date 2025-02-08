package com.vacation.platform.stayfinder.login.service;

import com.vacation.platform.stayfinder.login.dto.LogOutDTO;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    StayFinderResponseDTO<?> login(LoginDTO loginDTO);

    StayFinderResponseDTO<?> logout(String token, LogOutDTO logOutDTO);

    StayFinderResponseDTO<?> refreshToken(String email);

}
