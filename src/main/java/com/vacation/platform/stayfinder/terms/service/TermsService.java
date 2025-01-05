package com.vacation.platform.stayfinder.terms.service;

import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public interface TermsService {

    public Result<?> getTermsMain();

    public Result<?> registerTerms();

}
