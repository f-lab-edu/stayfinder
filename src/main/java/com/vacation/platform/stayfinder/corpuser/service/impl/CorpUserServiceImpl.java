package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.corpuser.dto.CorpUserDTO;
import com.vacation.platform.stayfinder.corpuser.entity.CorpStatus;
import com.vacation.platform.stayfinder.corpuser.entity.CorporateUser;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRepository;
import com.vacation.platform.stayfinder.corpuser.repository.CorporateUserRepository;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserService;
import com.vacation.platform.stayfinder.login.dto.LogOutDTO;
import com.vacation.platform.stayfinder.login.dto.LoginDTO;
import com.vacation.platform.stayfinder.user.entity.Role;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserServiceImpl implements CorpUserService {

    private final CorpUserRepository corpUserRepository;

    private final CorporateUserRepository corporateUserRepository;

    @Override
    @Transactional
    public StayFinderResponseDTO<?> createCorpUser(CorpUserDTO corpUserDTO) {
         corpUserRepository.findByBusinessLicense(corpUserDTO.getBusinessLicense(), CorpStatus.REGISTERED)
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
        corporateUser.setBusinessLicense(corpUserDTO.getBusinessLicense());
        corporateUser.setPhoneNumber(corpUserDTO.getCorpPhoneNumber());
        corporateUser.setRole(Role.ROLE_CORP_USER);

        corporateUserRepository.save(corporateUser);

        return StayFinderResponseDTO.success();
    }

    @Override
    public StayFinderResponseDTO<?> login(LoginDTO loginDTO) {
        return StayFinderResponseDTO.success();
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
