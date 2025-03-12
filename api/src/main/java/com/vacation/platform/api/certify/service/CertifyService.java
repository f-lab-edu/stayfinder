package com.vacation.platform.api.certify.service;

import com.vacation.platform.api.certify.dto.CertifyRequestDto;
import com.vacation.platform.api.util.StayFinderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CertifyService {

    StayFinderResponseDTO<?> reqSend(CertifyRequestDto certifyRequestDto);

    StayFinderResponseDTO<?> certifyNumberProve(CertifyRequestDto certifyRequestDto);

    StayFinderResponseDTO<?> certifyDelete(CertifyRequestDto certifyRequestDto);

}
