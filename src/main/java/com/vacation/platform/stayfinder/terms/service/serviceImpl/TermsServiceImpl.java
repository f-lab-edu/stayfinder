package com.vacation.platform.stayfinder.terms.service.serviceImpl;

import com.vacation.platform.stayfinder.terms.repository.TermsRepository;
import com.vacation.platform.stayfinder.terms.repository.TermsSubRepository;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.util.Result;
import org.springframework.stereotype.Service;

@Service
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    private final TermsSubRepository termsSubRepository;

    public TermsServiceImpl(TermsRepository termsRepository, TermsSubRepository termsSubRepository) {
        this.termsRepository = termsRepository;
        this.termsSubRepository = termsSubRepository;
    }

    @Override
    public Result<?> getTermsMain() {
        return null;
    }

    @Override
    public Result<?> registerTerms() {
        return null;
    }
}
