package com.vacation.platform.stayfinder.certify.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface TermsUserAgreementService {

    public Result<?> registerUserTerms(TermsDto termsDto);

}
