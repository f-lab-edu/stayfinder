package com.vacation.platform.stayfinder.corpuser.service;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserRequestDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CorpUserRequestService {

    StayFinderResponseDTO<?> approvalRequest(CorpUserRequestDTO corpUserRequestDTO);

}
