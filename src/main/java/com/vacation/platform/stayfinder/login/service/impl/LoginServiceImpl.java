package com.vacation.platform.stayfinder.login.service.impl;

import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.login.repository.UserAuthRepository;
import com.vacation.platform.stayfinder.login.service.LoginService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public ResponseEntity<StayFinderResponseDTO<?>> login(LoginDTO loginDTO) {
        return null;
    }

}
