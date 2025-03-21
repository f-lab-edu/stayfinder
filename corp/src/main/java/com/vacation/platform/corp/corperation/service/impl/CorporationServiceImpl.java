package com.vacation.platform.corp.corperation.service.impl;

import com.vacation.platform.api.common.ErrorType;
import com.vacation.platform.api.common.StayFinderException;
import com.vacation.platform.api.login.dto.JwtTokenResponse;
import com.vacation.platform.api.login.dto.LogOutDTO;
import com.vacation.platform.api.login.dto.LoginDTO;
import com.vacation.platform.api.login.dto.LoginResponseDTO;
import com.vacation.platform.api.login.service.impl.LoginServiceImpl;
import com.vacation.platform.api.user.entity.Role;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import com.vacation.platform.corp.corperation.dto.CorpUserDTO;
import com.vacation.platform.corp.corperation.entity.CorpStatus;
import com.vacation.platform.corp.corperation.entity.CorporateUser;
import com.vacation.platform.corp.corperation.repository.CorporateUserRepository;
import com.vacation.platform.corp.corperation.repository.corporationRepository;
import com.vacation.platform.corp.corperation.service.CorporationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorporationServiceImpl implements CorporationService {

    private final corporationRepository corporationRepository;

    private final CorporateUserRepository corporateUserRepository;

    private final LoginServiceImpl loginServiceImpl;

    @Override
    @Transactional
    public StayFinderResponseDTO<?> createCorporationUser(CorpUserDTO corpUserDTO) {
         corporationRepository.findByBusinessLicense(corpUserDTO.getBusinessLicense(), CorpStatus.REGISTERED)
                .orElseThrow( () -> new StayFinderException(ErrorType.BUSINESS_LICENSE_IS_NOT_VALID,
                        Map.of("businessLicense", corpUserDTO.getBusinessLicense()), log::error));

        corporateUserRepository.findByEmail(corpUserDTO.getEmail())
                .ifPresent( corpUser -> {
                    throw new StayFinderException(ErrorType.EMAIL_IS_ALREADY_EXIST,
                            Map.of("email", corpUserDTO.getEmail()), log::error);
                });

        CorporateUser corporateUser = new CorporateUser();
        corporateUser.setEmail(corpUserDTO.getEmail());
        corporateUser.setPassword(corpUserDTO.getPassword());
        corporateUser.setPhoneNumber(corpUserDTO.getCorpPhoneNumber());
        corporateUser.setRole(Role.ROLE_CORP_USER);

        corporateUserRepository.save(corporateUser);

        return StayFinderResponseDTO.success();
    }

    @Override
    public StayFinderResponseDTO<?> login(LoginDTO loginDTO) {
        CorporateUser corporateUser = corporateUserRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow( () ->
                    new StayFinderException(ErrorType.DUPLICATE_EMAIL,
                            Map.of("email", loginDTO.getEmail()), log::error)
                );

        loginServiceImpl.passwordValidate(loginDTO);

        JwtTokenResponse accessTokenResponse = loginServiceImpl.getAccessToken(loginDTO, Map.of("role", corporateUser.getRole()));

        JwtTokenResponse refreshTokenResponse = loginServiceImpl.getRefreshToken(loginDTO, Map.of("role", corporateUser.getRole()));

        loginServiceImpl.TokenSaves(accessTokenResponse, refreshTokenResponse, loginDTO);

        return StayFinderResponseDTO.success(new LoginResponseDTO(accessTokenResponse.getToken(), refreshTokenResponse.getToken()));
    }

    @Override
    public StayFinderResponseDTO<?> logout(LogOutDTO logOutDTO) {
        return StayFinderResponseDTO.success();
    }

    @Override
    public StayFinderResponseDTO<?> refreshToken(String refreshToken) {
        return StayFinderResponseDTO.success();
    }
}
