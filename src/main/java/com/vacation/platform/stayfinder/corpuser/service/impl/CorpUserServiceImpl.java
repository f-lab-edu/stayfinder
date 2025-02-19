package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserDTO;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserServiceImpl implements CorpUserService {

    @Override
    public StayFinderResponseDTO<?> createCorpUser(CorpUserDTO CorpUserDTO) {
        return StayFinderResponseDTO.success();
    }
}
