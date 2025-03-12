package com.vacation.platform.corp.corpuser.service;

import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corpuser.dto.CorpUserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface CorpUserService {
    StayFinderResponseDTO<?> createCorpUser(@RequestBody CorpUserDTO corpUserDTO);

    StayFinderResponseDTO<?> login(@RequestBody LoginDTO loginDTO);

    StayFinderResponseDTO<?> logout(@RequestBody LogOutDTO logOutDTO);

    StayFinderResponseDTO<?> refreshToken(String refreshToken);
}
