package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface TermsService {

    public Result<Terms> getTermsMain();

    public Result<?> registerTerms (TermsDto termsDto);

}
