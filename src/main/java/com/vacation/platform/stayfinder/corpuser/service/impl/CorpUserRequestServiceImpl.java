package com.vacation.platform.stayfinder.corpuser.service.impl;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.corpuser.repository.CorpUserRequestRepository;
import com.vacation.platform.stayfinder.corpuser.service.CorpUserRequestService;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorpUserRequestServiceImpl implements CorpUserRequestService {

    private final CorpUserRequestRepository corpUserRequestRepository;

    @Override
    public StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO) {
        return StayFinderResponseDTO.success();
    }
}
