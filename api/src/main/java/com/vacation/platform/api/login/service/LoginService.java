package com.vacation.platform.api.login.service;

import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    StayFinderResponseDTO<?> login(LoginDTO loginDTO);

    StayFinderResponseDTO<?> logout(String token, LogOutDTO logOutDTO);

    StayFinderResponseDTO<?> refreshToken(String refreshToken);

}
