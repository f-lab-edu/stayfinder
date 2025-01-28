package com.vacation.platform.stayfinder.certify.service;

import com.vacation.platform.stayfinder.certify.dto.CertifyRequestDto;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CertifyService {

    ResponseEntity<StayFinderResponseDTO<?>> reqSend(CertifyRequestDto certifyRequestDto);

    ResponseEntity<StayFinderResponseDTO<?>> certifyNumberProve(CertifyRequestDto certifyRequestDto);

    ResponseEntity<StayFinderResponseDTO<?>> certifyDelete(CertifyRequestDto certifyRequestDto);

}
