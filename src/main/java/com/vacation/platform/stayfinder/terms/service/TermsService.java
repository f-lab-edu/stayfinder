package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TermsService {

    Result<Terms> getTermsMain();

    Result<List<TermsSub>> getTermsSub(TermsDto termsDto);

    Result<?> registerTerms (TermsDto termsDto);

}
