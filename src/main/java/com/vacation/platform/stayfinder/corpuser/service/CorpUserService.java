package com.vacation.platform.stayfinder.corpuser.service;

import com.vacation.platform.stayfinder.corpuser.dto.CorpUserDTO;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface CorpUserService {
    StayFinderResponseDTO<?> createCorpUser(@RequestBody CorpUserDTO CorpUserDTO);
}
