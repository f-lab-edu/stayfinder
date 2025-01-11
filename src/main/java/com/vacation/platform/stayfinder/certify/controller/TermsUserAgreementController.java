package com.vacation.platform.stayfinder.certify.controller;

import com.vacation.platform.stayfinder.certify.service.TermsUserAgreementService;
import com.vacation.platform.stayfinder.certify.service.serviceImpl.TermsUserAgreementServiceImpl;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/terms_user")
public class TermsUserAgreementController {

    private final TermsUserAgreementService termsUserAgreementService;

    public TermsUserAgreementController(TermsUserAgreementServiceImpl termsUserAgreementService) {
        this.termsUserAgreementService = termsUserAgreementService;
    }

    // 유저 약관 등록 api
    @PostMapping("/agreement")
    public Result<?> registerAgreement(@Valid @RequestBody TermsDto termsDto) {
        return termsUserAgreementService.registerUserTerms(termsDto);
    }

}
