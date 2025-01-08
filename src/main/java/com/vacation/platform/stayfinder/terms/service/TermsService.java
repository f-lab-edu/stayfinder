package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.entity.TermsSub;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TermsService {

    public Result<List<Terms>> getTermsMain();

    public Result<List<TermsSub>> getTermsSub(TermsDto termsDto);

    public Result<?> registerTerms (TermsDto termsDto);

}
