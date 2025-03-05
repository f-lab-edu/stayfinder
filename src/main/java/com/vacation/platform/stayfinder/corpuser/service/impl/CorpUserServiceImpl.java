package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserDTO;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRepository;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserService;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserServiceImpl implements CorpUserService {

    private final UserRepository userRepository;

    private final CorpUserRepository corpUserRepository;

    private final CorpUserRequestRepository corpUserRequestRepository;

    @Override
    @Transactional
    public StayFinderResponseDTO<?> createCorpUser(CorpUserDTO CorpUserDTO) {


        return StayFinderResponseDTO.success();
    }
}
