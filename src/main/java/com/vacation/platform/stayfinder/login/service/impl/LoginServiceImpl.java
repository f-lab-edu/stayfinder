package com.vacation.platform.stayfinder.login.service.impl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.login.dto.JwtTokenResponse;
import com.vacation.platform.stayfinder.login.dto.LogOutDTO;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.login.dto.LoginResponseDTO;
import com.vacation.platform.stayfinder.login.service.LoginService;
import com.vacation.platform.stayfinder.login.service.RefreshTokenRedisService;
import com.vacation.platform.stayfinder.login.service.TokenBlocklistService;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.util.JwtUtil;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RefreshTokenRedisService refreshTokenRedisService;

    private final TokenBlocklistService tokenBlocklistService;

    private final UserRepository userRepository;

    @Override
    public StayFinderResponseDTO<?> login(LoginDTO loginDTO) {
        userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new StayFinderException(
                        ErrorType.USER_EMAIL_NOT_EXIST,
                        Map.of("email", loginDTO.getEmail()),
                        log::error,
                        null
                ));

        String encodePassword = bCryptPasswordEncoder.encode(loginDTO.getPassword());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), encodePassword)) {
            throw new StayFinderException(ErrorType.USER_PASSWORD_NOT_MATCHED,
                    Map.of("password", loginDTO.getPassword()),
                    log::error,
                    null
            );
        }

        JwtTokenResponse accessTokenResponse = jwtUtil.generateAccessToken(loginDTO.getEmail());
        JwtTokenResponse refreshTokenResponse = jwtUtil.generateRefreshToken(loginDTO.getEmail());

        refreshTokenRedisService.saveToken(loginDTO.getEmail().concat("_accessToken"), accessTokenResponse.getToken());
        refreshTokenRedisService.saveToken(loginDTO.getEmail().concat("_refreshToken"), refreshTokenResponse.getToken());

        return StayFinderResponseDTO.success(new LoginResponseDTO(accessTokenResponse.getToken(), refreshTokenResponse.getToken()));
    }

    @Override
    public StayFinderResponseDTO<?> logout(String token, LogOutDTO logOutDTO) {
        if(!jwtUtil.validateToken(token)) {
            throw new StayFinderException(ErrorType.ACCESS_TOKEN_NOT_VALID,
                    Map.of("token", token),
                    log::error,
                    null);
        }

        if(!jwtUtil.validateToken(token, logOutDTO.getEmail())) {
            throw new StayFinderException(ErrorType.TOKEN_IS_NOT_VALID,
                    Map.of("logOutDTO", logOutDTO),
                    log::error,
                    null);
        }

        long expiration = 1000 * 60 * 15; // Access Token 만료 시간 (15분)

        tokenBlocklistService.addToBlocklist(token, expiration);

        refreshTokenRedisService.deleteToken(logOutDTO.getEmail());

        return StayFinderResponseDTO.success();
    }

    @Override
    public StayFinderResponseDTO<?> refreshToken(String email) {


        return StayFinderResponseDTO.success();
    }

}