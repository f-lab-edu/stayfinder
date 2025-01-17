package com.vacation.platform.stayfinder.terms.controller;

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
        try {
            return termsService.registerTerms(termsDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
