package com.vacation.platform.stayfinder.certify.service;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CertifyService {

    StayFinderResponseDTO<?> reqSend(CertifyRequestDto certifyRequestDto);

    StayFinderResponseDTO<?> certifyNumberProve(CertifyRequestDto certifyRequestDto);

    StayFinderResponseDTO<?> certifyDelete(CertifyRequestDto certifyRequestDto);

}
