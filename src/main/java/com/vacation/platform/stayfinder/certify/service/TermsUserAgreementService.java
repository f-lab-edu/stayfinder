package com.vacation.platform.stayfinder.certify.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.util.StayFinderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TermsUserAgreementService {

    ResponseEntity<StayFinderResponseDTO<?>> registerUserTerms(TermsDto termsDto);

}
