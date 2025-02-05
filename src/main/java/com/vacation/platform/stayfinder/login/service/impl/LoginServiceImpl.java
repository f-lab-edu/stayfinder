package com.vacation.platform.stayfinder.login.service.impl;

import com.vacation.platform.stayfinder.certify.service.serviceImpl.SequenceService;
import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.login.dto.JwtTokenResponse;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.login.entity.UserAuth;
import com.vacation.platform.stayfinder.login.repository.UserAuthRepository;
import com.vacation.platform.stayfinder.login.service.LoginService;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.util.JwtUtil;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserAuthRepository userAuthRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SequenceService sequenceService;

    @Override
    public ResponseEntity<StayFinderResponseDTO<?>> login(LoginDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new StayFinderException(
                        ErrorType.USER_EMAIL_NOT_EXIST,
                        loginDTO.getEmail(),
                        x -> log.error("{}", ErrorType.USER_EMAIL_NOT_EXIST.getInternalMessage()),
                        null
                ));

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new StayFinderException(ErrorType.USER_PASSWORD_NOT_MATCHED,
                    loginDTO.getPassword(),
                    x -> log.error("{}", ErrorType.USER_PASSWORD_NOT_MATCHED.getInternalMessage()),
                    null
            );
        }

        userAuthRepository.findByUserId(user.getUserId()).ifPresent(x -> {
                    throw new StayFinderException(ErrorType.USER_AUTH_DUPLICATION,
                            user.getEmail(),
                            l -> log.error("{}", ErrorType.USER_AUTH_DUPLICATION.getExternalMessage()),
                            null);
        });

        JwtTokenResponse jwtTokenResponse = jwtUtil.generateRefreshToken(user.getEmail());

        UserAuth userAuth = new UserAuth();
        userAuth.setId(sequenceService.getNextUserAuthId());
        userAuth.setUserId(user.getUserId());
        userAuth.setRefreshToken(jwtTokenResponse.getToken());
        userAuth.setExpiresAt(jwtTokenResponse.getExpiryDate());

        userAuthRepository.save(userAuth);

        loginDTO.setAccessToken(jwtUtil.generateAccessToken(user.getEmail()));
        loginDTO.setRefreshToken(jwtTokenResponse.getToken());

        return ResponseEntity.ok(StayFinderResponseDTO.success(loginDTO));
    }

}
