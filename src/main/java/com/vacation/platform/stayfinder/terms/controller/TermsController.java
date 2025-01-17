package com.vacation.platform.stayfinder.terms.controller;


import com.vacation.platform.stayfinder.terms.dto.TermsDto;
import com.vacation.platform.stayfinder.terms.entity.Terms;
import com.vacation.platform.stayfinder.terms.service.TermsService;
import com.vacation.platform.stayfinder.terms.service.serviceImpl.TermsServiceImpl;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/terms_main")
public class TermsController {

    private final TermsService termsService;

    public TermsController(TermsServiceImpl termsService) {
        this.termsService = termsService;
    }

    //처음에 약관 동의 하는 api 전송
    //특정 고객이 약관 상세 보고 싶다면 해당 약관 sub 전송
    //메인 동의 api 에서 응답으로 필수 동의를 전체 동의 한 경우에 휴대폰 인증으로 넘어감
    @PostMapping("/getMain")
    public Result<Terms> getMain() {
        return termsService.getTermsMain();
    }

    @PostMapping("/terms_register")
    public Result<?> termsRegistration(@Valid @RequestBody TermsDto termsDto) {
        try {
            return termsService.registerTerms(termsDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
