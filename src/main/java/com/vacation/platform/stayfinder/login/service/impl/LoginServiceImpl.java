package com.vacation.platform.stayfinder.login.service.impl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.login.dto.JwtTokenResponse;
import com.vacation.platform.stayfinder.login.dto.LogOutDTO;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.login.dto.LoginResponseDTO;
import com.vacation.platform.stayfinder.login.service.LoginService;
import com.vacation.platform.stayfinder.login.service.TokenRedisService;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.util.JwtUtil;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenRedisService tokenRedisService;

    private final UserRepository userRepository;

    private final long ACCESS_TOKEN_TIME = 1000 * 60 * 15L;

    @Override
    public StayFinderResponseDTO<?> login(LoginDTO loginDTO) {
       User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new StayFinderException(
                        ErrorType.USER_EMAIL_NOT_EXIST,
                        Map.of("email", loginDTO.getEmail()),
                        log::error
                ));

        String encodePassword = bCryptPasswordEncoder.encode(loginDTO.getPassword());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), encodePassword)) {
            throw new StayFinderException(ErrorType.USER_PASSWORD_NOT_MATCHED,
                    Map.of("password", loginDTO.getPassword()),
                    log::error
            );
        }

        long refreshTokenTime = 1000 * 60 * 60 * 24 * 7L;

        JwtTokenResponse accessTokenResponse = jwtUtil.generateToken(loginDTO.getEmail(), ACCESS_TOKEN_TIME, Map.of("role", user.getRole()));

        JwtTokenResponse refreshTokenResponse = jwtUtil.generateToken(loginDTO.getEmail(), refreshTokenTime, Map.of("role", user.getRole()));

        tokenRedisService.saveToken(accessTokenResponse.getToken(), loginDTO.getEmail(), ACCESS_TOKEN_TIME, TimeUnit.DAYS);
        tokenRedisService.saveToken(loginDTO.getEmail(), refreshTokenResponse.getToken(), refreshTokenTime, TimeUnit.DAYS);

        return StayFinderResponseDTO.success(new LoginResponseDTO(accessTokenResponse.getToken(), refreshTokenResponse.getToken()));
    }

    @Override
    public StayFinderResponseDTO<?> logout(String token, LogOutDTO logOutDTO) {
        if(!jwtUtil.validateToken(token, logOutDTO.getEmail())) {
            throw new StayFinderException(ErrorType.TOKEN_IS_NOT_VALID,
                    Map.of("logOutDTO", logOutDTO),
                    log::error);
        }

        tokenRedisService.deleteToken(logOutDTO.getEmail());
        tokenRedisService.deleteToken(token);

        return StayFinderResponseDTO.success();
    }

    @Override
    public StayFinderResponseDTO<?> refreshToken(String refreshToken) {

        if(jwtUtil.validateToken(refreshToken)) {
            String email = jwtUtil.getUserEmail(refreshToken);

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new StayFinderException(
                            ErrorType.USER_EMAIL_NOT_EXIST,
                            Map.of("email", email),
                            log::error
                    ));

            JwtTokenResponse accessTokenResponse = jwtUtil.generateToken(email, ACCESS_TOKEN_TIME, Map.of("role", user.getRole()));


            tokenRedisService.saveToken(accessTokenResponse.getToken(), email, ACCESS_TOKEN_TIME, TimeUnit.DAYS);
            return StayFinderResponseDTO.success(accessTokenResponse.getToken());
        }

        throw new StayFinderException(ErrorType.TOKEN_IS_NOT_VALID, Map.of("refreshToken", refreshToken), log::error);
    }

}