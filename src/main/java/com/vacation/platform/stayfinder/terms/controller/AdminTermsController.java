package com.vacation.platform.stayfinder.terms.controller;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.terms.service.serviceImpl.TermsServiceImpl;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/terms")
public class AdminTermsController {

    private final TermsService termsService;

    public AdminTermsController(TermsServiceImpl termsService) {
        this.termsService = termsService;
    }

    @PostMapping("/register")
    public Result<?> termsRegistration(@Valid @RequestBody TermsDto termsDto) {
        if(termsDto == null) throw new StayFinderException(ErrorType.TERMS_NOT_FOUND);

        termsService.registerTerms(termsDto);

        return Result.success();
    }

}
